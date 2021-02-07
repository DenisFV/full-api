package ru.denisfv.fullapi.spring.data.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.denisfv.fullapi.spring.data.mongo.entity.FirstEntity;
import ru.denisfv.fullapi.spring.data.mongo.entity.SecondEntity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface MongoRepo extends MongoRepository<FirstEntity, Long> {

    FirstEntity findByName(String name);

    List<FirstEntity> findByNameLikeIgnoreCase(String likePattern);

    @Query(value = "{'secondEntities.name': {$regex:?0}}", fields = "{'secondEntities.name': 1}")
    List<FirstEntity> findBySecondEntityNamesWithSpecificSecondEntityNames(String partOfSecondEntityName);

    default List<String> findSecondEntityNamesWithSpecificSecondEntityNames(String partOfSecondEntityName){
        List<FirstEntity> list = findBySecondEntityNamesWithSpecificSecondEntityNames(partOfSecondEntityName);
        return list.stream()
                .map(FirstEntity::getSecondEntities)
                .flatMap(Collection::stream)
                .map(SecondEntity::getName)
                .collect(Collectors.toList());
    }

    List<FirstEntity> findByNameIn(List<String> names);

    @Query(value = "{name: {$gt:?0, $lt:?1}}")
    List<FirstEntity> findFirstEntitiesBetweenTwoNames(String startName, String endName);
}
