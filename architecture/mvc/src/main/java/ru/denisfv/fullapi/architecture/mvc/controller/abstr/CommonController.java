package ru.denisfv.fullapi.architecture.mvc.controller.abstr;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.util.ResponseEntityHelper;

import java.util.Map;

public interface CommonController<D extends AbstractEntity, K> {

    ResponseEntity head(K id);

    ResponseEntity<EntityModel<D>> findById(K id);

    ResponseEntity<CollectionModel<EntityModel<D>>> findAll();

    ResponseEntity<EntityModel<D>> create(D t);

    ResponseEntity<EntityModel<D>> update(D t);

    ResponseEntity<EntityModel<D>> deleteById(K id);

    ResponseEntity<CollectionModel<EntityModel<D>>> deleteAll();

    ResponseEntity<EntityModel<ResponseEntityHelper<D>>> find(String start, String limit, String sort, String search);

    ResponseEntity<EntityModel<Map<String, Object>>> count(String search);

    ResponseEntity test();
}