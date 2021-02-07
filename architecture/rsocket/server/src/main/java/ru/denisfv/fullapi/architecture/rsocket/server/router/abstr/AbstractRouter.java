package ru.denisfv.fullapi.architecture.rsocket.server.router.abstr;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.CommonService;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class AbstractRouter<D extends AbstractEntity<K>, S extends CommonService<D, K>, K>
        implements CommonRouter<D, K> {

    @NonNull S service;

    @MessageMapping("head")
    public Mono<Boolean> head(final K id) {
        log.info("Проверка сущетсвования объекта по id");

        return service.findById(id)
                .map(e -> true)
                .defaultIfEmpty(false);
    }

    @MessageMapping("findById")
    public Mono<D> findById(final K id) {
        log.info("Поиск объекта по id");

        return service.findById(id);
    }

    @MessageMapping("findAll")
    public Flux<D> findAll() {
        log.info("Поиск всех объектов");

        return service.findAll();
    }

    @MessageMapping("create")
    public Mono<D> create(@RequestBody final D t) {
        log.info("Сохранение объекта: {}", t);

        return service.create(t);
    }

    @MessageMapping("update")
    public Mono<D> update(@RequestBody final D t) {
        log.info("Обновление объекта: {}", t);

        return service.update(t);
    }

    @MessageMapping("deleteById")
    public Mono<D> deleteById(@PathVariable final K id) {
        log.info("Удаление объекта по id: {}", id);

        return service.deleteById(id);
    }

    @MessageMapping("deleteAll")
    public Flux<D> deleteAll() {
        log.info("Удаление всех объектов");

        return service.deleteAll();
    }

    @MessageMapping("test")
    public Mono<Void> test() {
        log.info("Проверка контролера");

        return Mono.empty();
    }

    @MessageExceptionHandler
    public Mono<String> handleException(Exception e) {
        return Mono.just(e.getMessage());
    }
}
