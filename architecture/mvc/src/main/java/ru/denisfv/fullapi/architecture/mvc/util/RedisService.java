package ru.denisfv.fullapi.architecture.mvc.util;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.abstr.CommonRepo;

import java.util.List;

import static ru.denisfv.fullapi.architecture.mvc.util.Constants.CONTROLLED_REDIS_PREFIX;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class RedisService<T extends AbstractEntity, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> {

    @NonNull
    R repo;
    @NonNull
    M mapper;

    public static String getCacheKey(Object val) {
        return CONTROLLED_REDIS_PREFIX + val;
    }

    @CachePut(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.mvc.util.RedisService).getCacheKey('all')")
    public List<D> findAll() {
        log.info("Всех объектов нет в кеше (они будут браться из БД и после закешируются)");
        List<D> list = mapper.entityListToDtoList(repo.findAll());
        if (!list.isEmpty()) {
            return list;
        }
        log.error("Объекта не сущетсвует");
        return null;
    }

    @Caching(
            cacheable = @Cacheable(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.mvc.util.RedisService).getCacheKey(#id)"),
            evict = @CacheEvict(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.mvc.util.RedisService).getCacheKey(#id)")
    )
    public D getFromCache(K id) {
        return null;
    }

    @CachePut(cacheNames = "redisCache", unless = "#result == null", key = "T(ru.denisfv.fullapi.architecture.mvc.util.RedisService).getCacheKey(#id)")
    public D populateCache(K id) {
        return repo.findById(id)
                .map(mapper::entityToDto)
                .orElseGet(() -> {
                    log.error("Объекта не сущетсвует");
                    return null;
                });
    }

    @CachePut(cacheNames = "redisCache", value = "redisCache", key = "T(ru.denisfv.fullapi.architecture.mvc.util.RedisService).getCacheKey(#d.id)")
    public D update(D d) {
        return repo.findById(d.getId())
                .map(e -> mapper.entityToDto(repo.save(mapper.dtoToEntity(d))))
                .orElseGet(() -> {
                    log.error("Ошибка при попытке обновить запись (Объекта с таким id не сущетсвует)");
                    return null;
                });
    }

    @Cacheable(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.mvc.util.RedisService).getCacheKey(#d.id)")
    public D create(D d) {
        return d;
    }

    @CacheEvict(cacheNames = "redisCache", key = "T(ru.denisfv.fullapi.architecture.mvc.util.RedisService).getCacheKey(#id)")
    public void flushById(K id) {
        log.info("Запись с id: {} успешно удалена из кеша" + id);
    }

    @CacheEvict(cacheNames = "redisCache", allEntries = true)
    public void flushAll() {
        log.info("Кеш полностью очищен");
    }
}
