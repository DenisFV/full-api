package ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractController<D extends AbstractEntity<K>, K> implements CommonController<D, K> {

    @NonNull
    final String PREFIX_URL = this.getClass().getAnnotation(RequestMapping.class).value()[0];

    @Autowired
    RSocketRequester requester;

    @Operation(summary = "Проверка сущетсвования объекта по id")
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public Mono<ResponseEntity> head(@Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final K id) {
        log.info("Проверка сущетсвования объекта по id");

        Mono<Boolean> router = requester
                .route(PREFIX_URL + ".head")
                .data(id)
                .retrieveMono(Boolean.class);

        return router
                .map(e -> ResponseEntity.noContent().build())
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .cast(ResponseEntity.class);
    }

    @Operation(summary = "Поиск объекта по id")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<D>> findById(@Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final K id) {
        log.info("Поиск объекта по id");

        return reqResp(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск всех объектов")
    @GetMapping("/")
    public Flux<D> findAll() {
        log.info("Поиск всех объектов");

        return reqStream();
    }

    @Operation(summary = "Сохранение объекта")
    @PostMapping("/")
    public Mono<ResponseEntity<D>> create(@RequestBody final D t) {
        log.info("Сохранение объекта: {}", t);

        return reqResp(t)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Обновление объекта")
    @PutMapping("/")
    public Mono<ResponseEntity<D>> update(@RequestBody final D t) {
        log.info("Обновление объекта: {}", t);

        return reqResp(t)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Удаление объекта по id")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<D>> deleteById(@Parameter(description = "Индентификатор объекта", example = "1") @PathVariable final K id) {
        log.info("Удаление объекта по id: {}", id);

        return reqResp(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удаление всех объектов")
    @DeleteMapping("/")
    public Flux<D> deleteAll() {
        log.info("Удаление всех объектов");

        return reqStream();
    }

    @Operation(summary = "Проверка контролера")
    @GetMapping("/test")
    public Mono<ResponseEntity> test() {
        log.info("Проверка контролера");

        fireForget(null);

        return Mono.just(ResponseEntity.ok().build());
    }

    protected Class<D> getClassEntity() {
        return (Class<D>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Mono<D> reqResp(Object data) {
        return route(data).retrieveMono(getClassEntity());
    }

    protected Flux<D> reqStream() {
        return route(null).retrieveFlux(getClassEntity());
    }

    protected Mono<Void> fireForget(Object data) {
        return route(data).send();
    }

    private RSocketRequester.RetrieveSpec route(Object data) {
        RSocketRequester.RequestSpec route = requester
                .route(PREFIX_URL + "." + getMethodName());

        return data == null ? route : route.data(data);
    }

    private String getMethodName() {
        List<String> stackTrace = Stream.of(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::getMethodName)
                .collect(Collectors.toList());

        return Stream.of(this.getClass().getMethods())
                .map(Method::getName)
                .filter(stackTrace::contains)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ошибка при определнии имени метода"));
    }
}
