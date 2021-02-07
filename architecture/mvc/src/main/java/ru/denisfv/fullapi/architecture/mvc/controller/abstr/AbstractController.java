package ru.denisfv.fullapi.architecture.mvc.controller.abstr;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.denisfv.fullapi.architecture.mvc.controller.link.abstr.DefaultLink;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.service.abstr.CommonService;
import ru.denisfv.fullapi.architecture.mvc.util.QuerySupportService;
import ru.denisfv.fullapi.architecture.mvc.util.ResponseEntityHelper;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractController<D extends AbstractEntity<K>, S extends CommonService<D, K>, K,
        C extends AbstractController<D, S, K, C, L>, L extends DefaultLink<D, C>> implements CommonController<D, K> {

    @NonNull
    final S service;
    @NonNull
    final L link;

    @Autowired
    protected Validator validator;

    @Operation(summary = "Проверка сущетсвования объекта по id")
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity head(@Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final K id) {
        log.info("Проверка сущетсвования объекта по id");

        return service.findById(id)
                .map(e -> {
                    validate(e);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск объекта по id")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<D>> findById(@Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final K id) {
        log.info("Поиск объекта по id");

        return service.findById(id)
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск всех объектов")
    @GetMapping("/")
    public ResponseEntity<CollectionModel<EntityModel<D>>> findAll() {
        log.info("Поиск всех объектов");

        return service.findAll()
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toCollectionModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Сохранение объекта")
    @PostMapping("/")
    public ResponseEntity<EntityModel<D>> create(@RequestBody final D t) {
        log.info("Сохранение объекта: {}", t);

        return service.create(t)
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Обновление объекта")
    @PutMapping("/")
    public ResponseEntity<EntityModel<D>> update(@RequestBody final D t) {
        log.info("Обновление объекта: {}", t);

        return service.update(t)
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удаление объекта по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<D>> deleteById(@Parameter(name = "Индентификатор объекта", example = "1") @PathVariable final K id) {
        log.info("Удаление объекта по id: {}", id);

        return service.deleteById(id)
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удаление всех объектов")
    @DeleteMapping("/")
    public ResponseEntity<CollectionModel<EntityModel<D>>> deleteAll() {
        log.info("Удаление всех объектов");

        return service.deleteAll()
                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toCollectionModel(e, (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    @Operation(summary = "Возвращает список элементов в соответствии с параметрами")
    public ResponseEntity<EntityModel<ResponseEntityHelper<D>>> find(
            @Parameter(description = "Стартовая позиция", example = "1") @RequestParam(value = "start", required = false) final String start,
            @Parameter(description = "Конечная позиция", example = "10") @RequestParam(value = "limit", required = false) final String limit,
            @Parameter(description = "Сортировка", examples = {
                    @ExampleObject(name = "atr-", value = "atr-"),
                    @ExampleObject(name = "atr2", value = "atr2"),
                    @ExampleObject(name = "atr3", value = "atr3")
            }) @RequestParam(value = "sort", required = false) final String sort,
            @Parameter(description = "RSQL поиск", example = "login=='admin'") @RequestParam(value = "search", required = false) final String search
    ) {
        Map<String, Object> param = QuerySupportService.getQueryParam(start, limit, sort, search, null);

        return service.find(param)
                .map(e -> link.toCollectionModel(e, (C) this))

                .stream()
                .map(e -> new ResponseEntityHelper<>(
                        (Integer) param.getOrDefault("start", 0),
                        (Integer) param.getOrDefault("limit", 0),
                        (String) param.getOrDefault("sort", ""),
                        service.count(param)
                                .orElse(0L),
                        e)
                )
                .findFirst()

                .map(e -> {
                    validate(e);
                    return ResponseEntity.ok(link.toModel(e, e.getEntity(), (C) this));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/count")
    @Operation(summary = "Возвращает количество список элементов в соответствии с параметрами")
    public ResponseEntity<EntityModel<Map<String, Object>>> count(
            @Parameter(description = "RSQL поиск", example = "login=='admin'") @RequestParam(value = "search", required = false) final String search
    ) {
        Map<String, Object> param = QuerySupportService.getQueryParam(null, null, null, search, null);

        Map<String, Object> map = new HashMap<>();
        map.put("search", param.getOrDefault("search", ""));

        service.count(param)
                .ifPresentOrElse(
                        e -> map.put("count", e),
                        () -> map.put("count", 0L)
                );

        return Optional.of(map)
                .map(e -> ResponseEntity.ok(EntityModel.of(e, link.addLinks(service.getEmptyElement(), (C) this))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Проверка контролера")
    @GetMapping("/test")
    public ResponseEntity test() {
        log.info("Проверка контролера");

        return ResponseEntity.ok().build();
    }

    /**
     * Валидирует обьект
     *
     * @param entity сущность
     * @param <V>    тип обьекта
     */
    protected <V> void validate(final V entity) {
        Set<ConstraintViolation<V>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            throw new javax.validation.ConstraintViolationException(constraintViolations);
        }
    }
}
