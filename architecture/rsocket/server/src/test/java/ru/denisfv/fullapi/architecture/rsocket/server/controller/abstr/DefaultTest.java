package ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr.CommonRepo;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

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
    protected void headHttp() {
        this.client.head()
                .uri("/" + url + "/" + dto.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @SneakyThrows
    @Test
    protected void findById() {
        this.client.get()
                .uri("/" + url + "/" + dto.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void findAll() {
        this.client.get()
                .uri("/" + url + "/")
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void create() {
        this.client.post()
                .uri("/" + url + "/")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void update() {
        this.client.put()
                .uri("/" + url + "/")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void deleteById() {
        this.client.delete()
                .uri("/" + url + "/" + dto.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void deleteAll() {
        this.client.delete()
                .uri("/" + url + "/")
                .exchange()
                .expectStatus().isOk();
    }
}
