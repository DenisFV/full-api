package ru.denisfv.fullapi.architecture.rsocket.server.router.abstr;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr.CommonRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.util.RedisService;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void flushById() {
        Mono<D> router = reqResp(route("flushById", dto.getId()));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void flushAll() {
        Flux<D> router = reqStream(route("flushAll", null));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void findAllKeys() {
        Flux<String> router = requester
                .route(url + ".findAllKeys")
                .retrieveFlux(String.class);

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo("all"))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void findAllCache() {
        Flux<D> router = reqStream(route("findAllCache", null));

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(dto))
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    public void getTtlById() {
        Mono<Long> router = requester
                .route(url + ".getTtlById")
                .data(String.valueOf(dto.getId()))
                .retrieveMono(Long.class);

        StepVerifier
                .create(router)
                .consumeNextWith(message -> assertThat(message).isEqualTo(-1L))
                .verifyComplete();
    }
}
