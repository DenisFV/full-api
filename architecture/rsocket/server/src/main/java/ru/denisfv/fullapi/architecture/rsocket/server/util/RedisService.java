package ru.denisfv.fullapi.architecture.rsocket.server.util;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr.CommonRepo;

import static ru.denisfv.fullapi.architecture.rsocket.server.util.Constants.CONTROLLED_REDIS_PREFIX;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisService<T extends AbstractEntity, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> {

    @NonNull
    R repo;
    @NonNull
    M mapper;

    public static String getCacheKey(Object val) {
        return CONTROLLED_REDIS_PREFIX + val;
    }

    @CachePut(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.reactor.util.RedisService).getCacheKey('all')")
    public Flux<D> findAll() {
        log.info("Всех объектов нет в кеше (они будут браться из БД и после закешируются)");
        return repo.findAll()
                .map(mapper::entityToDto)
                .switchIfEmpty(Flux.defer(() -> {
                    log.error("Object do not exist");
                    return Flux.empty();
                }));
    }

    @Caching(
            cacheable = @Cacheable(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.reactor.util.RedisService).getCacheKey(#id)"),
            evict = @CacheEvict(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.reactor.util.RedisService).getCacheKey(#id)")
    )
    public Mono<D> getFromCache(K id) {
        return Mono.empty();
    }

    @CachePut(cacheNames = "redisCache", unless = "#result == null", key = "T(ru.denisfv.fullapi.architecture.reactor.util.RedisService).getCacheKey(#id)")
    public Mono<D> populateCache(K id) {
        return repo.findById(id)
                .map(mapper::entityToDto)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Object do not exist");
                    return Mono.empty();
                }));
    }

    @CachePut(cacheNames = "redisCache", value = "redisCache", key = "T(ru.denisfv.fullapi.architecture.reactor.util.RedisService).getCacheKey(#d.id)")
    public Mono<D> update(D d) {
        return repo.findById(d.getId())
                .flatMap(e -> repo.save(mapper.dtoToEntity(d)).map(mapper::entityToDto))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Ошибка при попытке обновить запись (Объекта с таким id не сущетсвует)");
                    return Mono.empty();
                }));
    }

    @Cacheable(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.reactor.util.RedisService).getCacheKey(#d.id)")
    public Mono<D> create(Mono<D> d) {
        return d;
    }

    @CacheEvict(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.reactor.util.RedisService).getCacheKey(#id)")
    public void flushById(K id) {
        log.info("Запись с id: {} успешно удалена из кеша" + id);
    }

    @CacheEvict(cacheNames = "redisCache", allEntries = true)
    public void flushAll() {
        log.info("Кеш полностью очищен");
    }
}
