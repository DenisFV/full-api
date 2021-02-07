package ru.denisfv.fullapi.architecture.rsocket.client.abstr;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.abstr.AbstractEntity;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class DefaultTest<D extends AbstractEntity<K>, K> extends AbstractTest {

    @NonNull
    final String url;
    @NonNull
    final D dto;

//    @PostConstruct
//    protected void init() {
//        initMocks();
//    }
//
//    protected void initMocks() {
//        T entity = mapper.dtoToEntity(dto);
//
//        Mockito.
//                when(repo.findById((K) entity.getId()))
//                .thenReturn(Mono.just(entity));
//
//        Mockito.
//                when(repo.findAll())
//                .thenReturn(Flux.just(entity));
//
//        Mockito.
//                when(repo.save(entity))
//                .thenReturn(Mono.just(entity));
//
//        Mockito.
//                when(repo.saveAll(Collections.singletonList(entity)))
//                .thenReturn(Flux.just((entity)));
//    }

    @SneakyThrows
    @Test
    protected void headHttp() {
        this.client.head()
                .uri(url + "/" + dto.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @SneakyThrows
    @Test
    protected void findById() {
        this.client.get()
                .uri(url + "/" + dto.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void findAll() {
        this.client.get()
                .uri(url + "/")
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void create() {
        this.client.post()
                .uri(url + "/")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void update() {
        this.client.put()
                .uri(url + "/")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void deleteById() {
        this.client.delete()
                .uri(url + "/" + dto.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    protected void deleteAll() {
        this.client.delete()
                .uri(url + "/")
                .exchange()
                .expectStatus().isOk();
    }
}
