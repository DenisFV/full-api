package ru.denisfv.fullapi.architecture.rsocket.client.abstr;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.abstr.AbstractEntity;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class DefaultRedisTest<D extends AbstractEntity<K>, K> extends DefaultTest<D, K> {

    public DefaultRedisTest(String url, D dto) {
        super(url, dto);
    }

//    @Override
//    protected void initMocks() {
//        super.initMocks();
//
//        Mockito.
//                when(redisService.getFromCache(dto.getId()))
//                .thenReturn(Mono.just(dto));
//
//        Mockito.
//                when(redisService.populateCache(dto.getId()))
//                .thenReturn(Mono.just(dto));
//
//        Mockito.
//                when(redisService.findAll())
//                .thenReturn(Flux.just(dto));
//
//        Mockito.
//                when(redisService.update(dto))
//                .thenReturn(Mono.just(dto));
//
//        Mockito.
//                when(redisService.create(Mono.just(dto)))
//                .thenReturn(Mono.just(dto));
//
//        Mockito.
//                when(redisTemplate.keys("*"))
//                .thenReturn(Collections.singleton("all"));
//
//        Mockito.
//                when(redisTemplate.getExpire(REDIS_SAFETY_PREFIX + dto.getId()))
//                .thenReturn(-1L);
//    }

    @SneakyThrows
    @Test
    void flushById() {
        this.client.get()
                .uri(url + "/flush/" + dto.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    void flushAll() {
        this.client.get()
                .uri(url + "/flush/")
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    void findAllKeys() {
        this.client.get()
                .uri(url + "/keys/")
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    void findAllCache() {
        this.client.get()
                .uri(url + "/caches/")
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    void getTtlById() {
        this.client.get()
                .uri(url + "/ttl/" + dto.getId())
                .exchange()
                .expectStatus().isNotFound();
    }
}
