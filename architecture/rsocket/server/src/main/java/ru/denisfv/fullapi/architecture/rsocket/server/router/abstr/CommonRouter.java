package ru.denisfv.fullapi.architecture.rsocket.server.router.abstr;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;

public interface CommonRouter<D extends AbstractEntity, K> {

    Mono<Boolean> head(K id);

    Mono<D> findById(K id);

    Flux<D> findAll();

    Mono<D> create(D t);

    Mono<D> update(D t);

    Mono<D> deleteById(K id);

    Flux<D> deleteAll();

    Mono<Void> test();
}