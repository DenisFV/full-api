package ru.denisfv.fullapi.architecture.mvc.service.abstr.redis;

import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.service.abstr.CommonService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommonRedisService<D extends AbstractEntity, K> extends CommonService<D, K> {

    Optional<D> flushById(K id);

    Optional<List<D>> flushAll();

    Optional<Set<String>> findAllKeys();

    Optional<List<D>> findAllCache();

    Optional<Long> getTtlById(String key);
}