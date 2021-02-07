package ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr.redis;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr.CommonController;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;

public interface CommonRedisController<D extends AbstractEntity, K> extends CommonController<D, K> {

    Mono<ResponseEntity<D>> flushById(K id);

    Flux<D> flushAll();

    Flux<String> findAllKeys();

    Flux<D> findAllCache();

    Mono<ResponseEntity<Long>> getTtlById(String key);
}