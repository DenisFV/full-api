package ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.redis;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.CommonService;

public interface CommonRedisService<D extends AbstractEntity, K> extends CommonService<D, K> {

    Mono<D> flushById(K id);

    Flux<D> flushAll();

    Flux<String> findAllKeys();

    Flux<D> findAllCache();

    Mono<Long> getTtlById(String key);
}