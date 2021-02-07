package ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.redis;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.CommonRouter;

public interface CommonRedisRouter<D extends AbstractEntity, K> extends CommonRouter<D, K> {

    Mono<D> flushById(K id);

    Flux<D> flushAll();

    Flux<String> findAllKeys();

    Flux<D> findAllCache();

    Mono<Long> getTtlById(String key);
}