package ru.denisfv.fullapi.architecture.rsocket.server.service.abstr;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;

public interface CommonService<D extends AbstractEntity, K> {

    Mono<D> findById(K id);

    Flux<D> findAll();

    Mono<D> create(D dto);

    Mono<D> update(D dto);

    Mono<D> deleteById(K id);

    Flux<D> deleteAll();

    D getEmptyElement();

    Class<D> getClassEntity();
}