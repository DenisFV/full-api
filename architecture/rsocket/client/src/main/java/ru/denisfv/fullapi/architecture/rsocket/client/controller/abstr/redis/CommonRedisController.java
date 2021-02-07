package ru.denisfv.fullapi.architecture.rsocket.client.controller.abstr.redis;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.client.controller.abstr.CommonController;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.abstr.AbstractEntity;

public interface CommonRedisController<D extends AbstractEntity, K> extends CommonController<D, K> {

    Mono<ResponseEntity<D>> flushById(K id);

    Flux<D> flushAll();

    Flux<String> findAllKeys();

    Flux<D> findAllCache();

    Mono<ResponseEntity<Long>> getTtlById(String key);
}