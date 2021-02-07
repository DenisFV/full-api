package ru.denisfv.fullapi.architecture.rsocket.client.controller.abstr;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.abstr.AbstractEntity;

public interface CommonController<D extends AbstractEntity, K> {

    Mono<ResponseEntity> head(K id);

    Mono<ResponseEntity<D>> findById(K id);

    Flux<D> findAll();

    Mono<ResponseEntity<D>> create(D t);

    Mono<ResponseEntity<D>> update(D t);

    Mono<ResponseEntity<D>> deleteById(K id);

    Flux<D> deleteAll();

    Mono<ResponseEntity> test();
}