package ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr.redis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr.AbstractController;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;

@Slf4j
public abstract class AbstractRedisController<D extends AbstractEntity<K>, K> extends AbstractController<D, K>
        implements CommonRedisController<D, K> {

    public AbstractRedisController() {
        super();
    }

    @Operation(summary = "Очистка кеша от записи с id")
    @GetMapping("/flush/{id}")
    public Mono<ResponseEntity<D>> flushById(
            @Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final K id
    ) {
        log.info("Очистка кеша от записи с id: {}", id);

        return reqResp(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Очистка кеша от всех записей")
    @GetMapping("/flush")
    public Flux<D> flushAll() {
        log.info("Очистка кеша от всех записей");

        return reqStream();
    }

    @Operation(summary = "Поиск всех ключей кеша")
    @GetMapping("/keys")
    public Flux<String> findAllKeys() {
        log.info("Поиск всех ключей кеша");

        return requester
                .route(PREFIX_URL + ".findAllKeys")
                .retrieveFlux(String.class);
    }

    @Operation(summary = "Поиск всех объектов кеша")
    @GetMapping("/caches")
    public Flux<D> findAllCache() {
        log.info("Поиск всех объектов кеша");

        return reqStream();
    }

    @Operation(summary = "Поиск оставшегося времени жизни ключа кеша")
    @GetMapping("/ttl/{key}")
    public Mono<ResponseEntity<Long>> getTtlById(
            @Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final String key
    ) {
        log.info("Поиск оставшегося времени жизни ключа кеша: {}", key);

        Mono<Long> router = requester
                .route(PREFIX_URL + ".getTtlById")
                .data(key)
                .retrieveMono(Long.class);

        return router
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
