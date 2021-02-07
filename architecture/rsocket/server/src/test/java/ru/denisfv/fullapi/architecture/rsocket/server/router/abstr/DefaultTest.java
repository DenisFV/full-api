package ru.denisfv.fullapi.architecture.rsocket.server.router.abstr;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr.CommonRepo;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class DefaultTest<T extends AbstractEntity, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> extends AbstractTest {

    @NonNull
    final String url;
    @NonNull
    final D dto;

    M mapper;
    @MockBean
    R repo;

    @PostConstruct
    protected void init() {
        initMapper();
        initMocks();
    }

    @SneakyThrows
    private void initMapper() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();

        Optional<Type> mapper = Stream.of(types)
                .filter(e -> Stream.of(((Class) e).getGenericInterfaces()).
                        anyMatch(k -> k.getTypeName().contains("CommonMapper")))
                .findFirst();

        this.mapper = mapper
                .map(e -> (M) Mappers.getMapper((Class) e))
                .orElseThrow();
    }

    protected void initMocks() {
        T entity = mapper.dtoToEntity(dto);

        Mockito.
                when(repo.findById((K) entity.getId()))
                .thenReturn(Mono.just(entity));

        Mockito.
                when(repo.findAll())
                .thenReturn(Flux.just(entity));

        Mockito.
                when(repo.save(entity))
                .thenReturn(Mono.just(entity));

        Mockito.
                when(repo.saveAll(Collections.singletonList(entity)))
                .thenReturn(Flux.just((entity)));
    }

    @Order(1)
    @Test
    public void fixRestartRSocket() {}

    @SneakyThrows
    @Test
    public void head() {
        Mono<Boolean> router = requester
                .route(url + ".head")
                .data(dto.getId())
                .retrieveMono(Boolean.class);

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(true))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
        public void findById() {
        Mono<D> router = reqResp(route("findById", dto.getId()));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void findAll() {
        Flux<D> router = reqStream(route("findAll", null));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void create() {
        Mono<D> router = reqResp(route("create", dto));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void update() {
        Mono<D> router = reqResp(route("update", dto));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void deleteById() {
        Mono<D> router = reqResp(route("deleteById", dto.getId()));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void deleteAll() {
        Flux<D> router = reqStream(route("deleteAll", null));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    protected Mono<D> reqResp(RSocketRequester.RetrieveSpec route) {
        return route.retrieveMono((Class<D>) dto.getClass());
    }

    protected Flux<D> reqStream(RSocketRequester.RetrieveSpec route) {
        return route.retrieveFlux((Class<D>) dto.getClass());
    }

    protected Mono<Void> fireForget(RSocketRequester.RetrieveSpec route) {
        return route.send();
    }

    protected RSocketRequester.RetrieveSpec route(String methodName, Object data) {
        RSocketRequester.RequestSpec route = requester
                .route(url + "." + methodName);

        return data == null ? route : route.data(data);
    }
}
