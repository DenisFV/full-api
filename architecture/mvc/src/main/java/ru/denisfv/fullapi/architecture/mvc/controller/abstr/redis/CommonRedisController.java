package ru.denisfv.fullapi.architecture.mvc.controller.abstr.redis;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import ru.denisfv.fullapi.architecture.mvc.controller.abstr.CommonController;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import java.util.Set;

public interface CommonRedisController<D extends AbstractEntity, K> extends CommonController<D, K> {

    ResponseEntity<EntityModel<D>> flushById(K id);

    ResponseEntity<CollectionModel<EntityModel<D>>> flushAll();

    ResponseEntity<Set<String>> findAllKeys();

    ResponseEntity<CollectionModel<EntityModel<D>>> findAllCache();

    ResponseEntity<Long> getTtlById(String key);
}