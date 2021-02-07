package ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.redis;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr.CommonRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.AbstractService;
import ru.denisfv.fullapi.architecture.rsocket.server.util.RedisService;

import java.util.Objects;
import java.util.stream.Collectors;

import static ru.denisfv.fullapi.architecture.rsocket.server.util.Constants.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractRedisService<T extends AbstractEntity, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> extends AbstractService<T, D, R, M, K> implements CommonRedisService<D, K> {

    @NonNull
    final RedisService<T, D, R, M, K> redisService = new RedisService<>(repo, mapper);
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public AbstractRedisService(R repo) {
        super(repo);
    }

    @SneakyThrows
    private K getId(String key) {
        String typeName = getEmptyElement().getClass().getDeclaredField("id").getType().getName();
        return (K) (typeName.contains("String")
                ? key
                : Long.parseLong(String.valueOf(key)));
    }

    @Override
    public Mono<D> findById(K id) {
        log.info("id объекта на входе: {}", id);
        return redisService.getFromCache(id)
                .map(e -> {
                    log.info("Объект с id: '{}' есть в кеше. Он будет загружен из кеша", id);
                    return e;
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Объекта c id: '{}' нет в кеше. Он будет загружен из БД", id);
                    return redisService.populateCache(id);
                }));
    }

    @Override
    public Flux<D> findAll() {
        return redisService.findAll();
    }

    @Override
    public Mono<D> update(final D d) {
        log.info("Object update: {}", d);

        return Mono.just(d.getId())
                .flatMap(e -> redisService.update(d))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("An error occurred while trying to update a record. Object with id: {} does not exist", d.getId());
                    return Mono.empty();
                }));
    }

    @Override
    public Mono<D> create(final D d) {
        log.info("Creating a new object: {}", d);

        return redisService.create(repo.save(mapper.dtoToEntity(d)).map(mapper::entityToDto));
    }

    @Override
    public Mono<D> deleteById(K id) {
        log.info("id объекта на входе: {}", id);
        return repo.findById(id)
                .map(e -> {
                    repo.delete(e);
                    log.info("Запись с id: {} успешно удалена из БД", id);
                    flushById(id);
                    return mapper.entityToDto(e);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Не удалось удалить запись с id: {}. Объекта с таким id не сущетсвует", id);
                    return Mono.empty();
                }));
    }

    @Override
    public Flux<D> deleteAll() {
        try {
            Flux<D> flux = repo.findAll().map(mapper::entityToDto);
            repo.deleteAll();
            log.info("Все записи успешно удалены из БД");
            flushAll();
            return flux;
        } catch (Exception e) {
            log.info("Не удалось удалить все записи: {}", e.getMessage());
            return Flux.empty();
        }
    }

    public Mono<D> flushById(K id) {
        log.info("id объекта на входе: {}", id);
        return repo.findById(id)
                .map(e -> {
                    redisService.flushById(id);
                    return mapper.entityToDto(e);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Не удалось удалить запись из кеша с id: {}. Записи с таким id не сущетсвует", id);
                    return Mono.empty();
                }));
    }

    public Flux<D> flushAll() {
        try {
            Flux<D> flux = findAllCache();
            redisService.flushAll();
            return flux;
        } catch (Exception e) {
            log.info("Не удалось полностью очистить кеш: {}", e.getMessage());
            return Flux.empty();
        }
    }

    public Flux<String> findAllKeys() {
        return Flux.fromIterable(Objects.requireNonNull(redisTemplate.keys("*")).stream()
                .map(e -> {
                    String key = e.substring(e.indexOf("_") + 1);
                    log.info("key '{}'= {}", e, key);
                    return key;
                }).collect(Collectors.toSet()));
    }

    public Flux<D> findAllCache() {
        return findAllKeys()
                .flatMap(key -> {
                    if (key.contains("all")) {
                        return redisService.findAll()
                                .map(flux -> {
                                    log.info("key value '{}'= {}", REDIS_PREFIX + CONTROLLED_REDIS_PREFIX + "all", flux);
                                    return flux;
                                })
                                .switchIfEmpty(Flux.defer(() -> {
                                    log.info("Кеш пуст");
                                    return Flux.empty();
                                }));
                    } else {
                        return findById(getId(key))
                                .map(d -> {
                                    log.info("key value '{}'= {}", REDIS_SAFETY_PREFIX + key, d);
                                    return d;
                                }).flux()
                                .switchIfEmpty(Flux.defer(() -> {
                                    log.info("Объекта c таким id: '{}' не существует", key);
                                    return Flux.empty();
                                }));
                    }
                })
                .switchIfEmpty(Flux.empty());
    }

    public Mono<Long> getTtlById(String key) {
        log.info("id объекта на входе: {}", key);
        Long ttl = redisTemplate.getExpire(REDIS_SAFETY_PREFIX + key);
        if (Objects.equals(ttl, -1L)) {
            log.info("Оставшееся время жизни ключа кеша: '{}' - не указано", key);
        } else if (Objects.equals(ttl, -2L)) {
            log.info("Объекта c таким ключом: '{}' в кеше не существует", key);
        } else {
            log.info("Оставшееся время жизни ключа кеша: '{}' = {}", key, ttl);
        }
        return Mono.just(ttl);
    }
}