package ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr.CommonRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.util.RedisService;

import java.util.Collections;

import static ru.denisfv.fullapi.architecture.rsocket.server.util.Constants.REDIS_SAFETY_PREFIX;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class DefaultRedisTest<T extends AbstractEntity, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> extends DefaultTest<T, D, R, M, K> {

    @MockBean
    RedisService<T, D, R, M, K> redisService;

    @MockBean
    RedisTemplate<String, String> redisTemplate;

    public DefaultRedisTest(String url, D dto) {
        super(url, dto);
    }

    @Override
    protected void initMocks() {
        super.initMocks();

        Mockito.
                when(redisService.getFromCache(dto.getId()))
                .thenReturn(Mono.just(dto));

        Mockito.
                when(redisService.populateCache(dto.getId()))
                .thenReturn(Mono.just(dto));

        Mockito.
                when(redisService.findAll())
                .thenReturn(Flux.just(dto));

        Mockito.
                when(redisService.update(dto))
                .thenReturn(Mono.just(dto));

        Mockito.
                when(redisService.create(Mono.just(dto)))
                .thenReturn(Mono.just(dto));

        Mockito.
                when(redisTemplate.keys("*"))
                .thenReturn(Collections.singleton("all"));

        Mockito.
                when(redisTemplate.getExpire(REDIS_SAFETY_PREFIX + dto.getId()))
                .thenReturn(-1L);
    }

    @SneakyThrows
    @Test
    void flushById() {
        this.client.get()
                .uri("/" + url + "/flush/" + dto.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    void flushAll() {
        this.client.get()
                .uri("/" + url + "/flush/")
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    void findAllKeys() {
        this.client.get()
                .uri("/" + url + "/keys/")
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    void findAllCache() {
        this.client.get()
                .uri("/" + url + "/caches/")
                .exchange()
                .expectStatus().isOk();
    }

    @SneakyThrows
    @Test
    void getTtlById() {
        this.client.get()
                .uri("/" + url + "/ttl/" + dto.getId())
                .exchange()
                .expectStatus().isOk();
    }
}
