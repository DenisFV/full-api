package ru.denisfv.fullapi.architecture.mvc.controller.abstr.redis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.denisfv.fullapi.architecture.mvc.controller.abstr.AbstractController;
import ru.denisfv.fullapi.architecture.mvc.controller.link.abstr.DefaultRedisLink;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.service.abstr.redis.CommonRedisService;

import java.util.Set;

@Slf4j
public abstract class AbstractRedisController<D extends AbstractEntity<K>, S extends CommonRedisService<D, K>, K,
        C extends AbstractRedisController<D, S, K, C, L>, L extends DefaultRedisLink<D, C>>
        extends AbstractController<D, S, K, C, L> implements CommonRedisController<D, K> {

    public AbstractRedisController(S service, L link) {
        super(service, link);
    }

    @Operation(summary = "Очистка кеша от записи с id")
    @GetMapping("/flush/{id}")
    public ResponseEntity<EntityModel<D>> flushById(
            @Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final K id
    ) {
        log.info("Очистка кеша от записи с id: {}", id);
        return service.flushById(id)
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Очистка кеша от всех записей")
    @GetMapping("/flush")
    public ResponseEntity<CollectionModel<EntityModel<D>>> flushAll() {
        log.info("Очистка кеша от всех записей");
        return service.flushAll()
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toCollectionModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск всех ключей кеша")
    @GetMapping("/keys")
    public ResponseEntity<Set<String>> findAllKeys() {
        log.info("Поиск всех ключей кеша");
        return service.findAllKeys()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск всех объектов кеша")
    @GetMapping("/caches")
    public ResponseEntity<CollectionModel<EntityModel<D>>> findAllCache() {
        log.info("Поиск всех объектов кеша");
        return service.findAllCache()
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toCollectionModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск оставшегося времени жизни ключа кеша")
    @GetMapping("/ttl/{key}")
    public ResponseEntity<Long> getTtlById(
            @Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final String key
    ) {
        log.info("Поиск оставшегося времени жизни ключа кеша: {}", key);
        return service.getTtlById(key)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
