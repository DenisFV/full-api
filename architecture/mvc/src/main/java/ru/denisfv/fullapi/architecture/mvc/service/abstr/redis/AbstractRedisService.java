package ru.denisfv.fullapi.architecture.mvc.service.abstr.redis;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.abstr.CommonRepo;
import ru.denisfv.fullapi.architecture.mvc.service.abstr.AbstractService;
import ru.denisfv.fullapi.architecture.mvc.util.RedisService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.denisfv.fullapi.architecture.mvc.util.Constants.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbstractRedisService<T extends AbstractEntity<K>, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> extends AbstractService<T, D, R, M, K> implements CommonRedisService<D, K> {

    @NonNull
    final RedisService<T, D, R, M, K> redisService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public AbstractRedisService(R repo) {
        super(repo);
        this.redisService = new RedisService<>(repo, mapper);
    }

    @Override
    public Optional<D> findById(K id) {
        log.info("id объекта на входе: {}", id);
        return Optional.ofNullable(redisService.getFromCache(id))
                .map(e -> {
                    log.info("Объект с id: '{}' есть в кеше. Он будет загружен из кеша", id);
                    return Optional.of(e);
                })
                .orElseGet(() -> {
                    log.info("Объекта c id: '{}' нет в кеше. Он будет загружен из БД", id);
                    return Optional.ofNullable(redisService.populateCache(id));
                });
    }

    @Override
    public Optional<List<D>> findAll() {
        return Optional.ofNullable(redisService.findAll());
    }

    @Override
    public Optional<D> update(final D d) {
        log.info("Object update: {}", d);

        return Optional.ofNullable(d.getId())
                .map(e -> Optional.ofNullable(redisService.update(d)))
                .orElseGet(() -> {
                    log.error("An error occurred while trying to update a record. Object with id: {} does not exist", d.getId());
                    return Optional.empty();
                });
    }

    @Override
    public Optional<D> create(final D d) {
        log.info("Creating a new object: {}", d);

        return Optional.ofNullable(redisService.create(
                mapper.entityToDto(repo.save(mapper.dtoToEntity(d)))));
    }

    @Override
    public Optional<D> deleteById(K id) {
        log.info("id объекта на входе: {}", id);
        return repo.findById(id)
                .map(e -> {
                    repo.delete(e);
                    log.info("Запись с id: {} успешно удалена из БД", id);
                    flushById(id);
                    return Optional.of(mapper.entityToDto(e));
                })
                .orElseGet(() -> {
                    log.info("Не удалось удалить запись с id: {}. Объекта с таким id не сущетсвует", id);
                    return Optional.empty();
                });
    }

    @Override
    public Optional<List<D>> deleteAll() {
        try {
            List<D> list = mapper.entityListToDtoList(repo.findAll());
            repo.deleteAll();
            log.info("Все записи успешно удалены из БД");
            flushAll();
            return Optional.of(list);
        } catch (Exception e) {
            log.info("Не удалось удалить все записи: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<D> flushById(K id) {
        log.info("id объекта на входе: {}", id);
        return repo.findById(id)
                .map(e -> {
                    redisService.flushById(id);
                    return Optional.of(mapper.entityToDto(e));
                })
                .orElseGet(() -> {
                    log.info("Не удалось удалить запись из кеша с id: {}. Записи с таким id не сущетсвует", id);
                    return Optional.empty();
                });
    }

    public Optional<List<D>> flushAll() {
        try {
            Optional<List<D>> list = findAllCache();
            redisService.flushAll();
            return list;
        } catch (Exception e) {
            log.info("Не удалось полностью очистить кеш: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Set<String>> findAllKeys() {
        return Optional.of(Objects.requireNonNull(redisTemplate.keys("*")).stream()
                .map(e -> {
                    String key = e.substring(e.indexOf("_") + 1);
                    log.info("key '{}'= {}", e, key);
                    return key;
                }).collect(Collectors.toSet()));
    }

    public Optional<List<D>> findAllCache() {
        return findAllKeys()
                .map(keys -> {
                    if (keys.contains("all")) {
                        return Optional.ofNullable(Optional.ofNullable(redisService.findAll())
                                .map(list -> {
                                    list.forEach(d -> log.info("key value '{}'= {}", REDIS_PREFIX + CONTROLLED_REDIS_PREFIX + "all", d));
                                    return list;
                                })
                                .orElseGet(() -> {
                                    log.info("Кеш пуст");
                                    return null;
                                }));
                    } else {
                        return Optional.of(keys.stream()

                                .map(key -> findById((K) key) //TODO каст не обработан
                                        .map(d -> {
                                            log.info("key value '{}'= {}", REDIS_SAFETY_PREFIX + key, d);
                                            return d;
                                        })
                                        .orElseGet(() -> {
                                            log.info("Объекта c таким id: '{}' не существует", key);
                                            return null;
                                        })
                                )
                                .collect(Collectors.toList()));
                    }
                })
                .orElseGet(Optional::empty);
    }

    public Optional<Long> getTtlById(String key) {
        log.info("id объекта на входе: {}", key);
        Long ttl = redisTemplate.getExpire(REDIS_SAFETY_PREFIX + key);
        if (Objects.equals(ttl, -1L)) {
            log.info("Оставшееся время жизни ключа кеша: '{}' - не указано", key);
        } else if (Objects.equals(ttl, -2L)) {
            log.info("Объекта c таким ключом: '{}' в кеше не существует", key);
        } else {
            log.info("Оставшееся время жизни ключа кеша: '{}' = {}", key, ttl);
            return Optional.ofNullable(ttl);
        }
        return Optional.empty();
    }
}