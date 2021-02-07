package ru.denisfv.fullapi.architecture.mvc.service.abstr;

import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommonService<D extends AbstractEntity, K> {

    Optional<D> findById(K id);

    Optional<List<D>> findAll();

    Optional<D> create(D t);

    Optional<D> update(D t);

    Optional<List<D>> saveAll(List<D> tList);

    Optional<D> deleteById(K id);

    Optional<List<D>> deleteAll();

    Optional<List<D>> find(Map<String, Object> param);

    Optional<Long> count(Map<String, Object> param);

    D getEmptyElement();
}