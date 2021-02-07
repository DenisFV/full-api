//package ru.denisfv.fullapi.spring.data.neo4j.repo;
//
//import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
//import ru.denisfv.fullapi.spring.data.neo4j.entity.FirstEntity;
//
//import java.util.List;
//
//public interface Neo4jRepo extends ReactiveNeo4jRepository<FirstEntity, String> {
//
//    FirstEntity findByName(String name);
//
//    List<FirstEntity> findByNameLike(String likePattern);
//
////    @Query(value = "{'secondEntities.name': {$regex:?0}}")
////    List<FirstEntity> findBySecondEntityNamesWithSpecificSecondEntityNames(String partOfSecondEntityName);
////
////    default List<String> findSecondEntityNamesWithSpecificSecondEntityNames(String partOfSecondEntityName){
////        List<FirstEntity> list = findBySecondEntityNamesWithSpecificSecondEntityNames(partOfSecondEntityName);
////        return list.stream()
////                .map(FirstEntity::getSecondEntities)
////                .flatMap(Collection::stream)
////                .map(SecondEntity::getName)
////                .collect(Collectors.toList());
////    }
////
////    List<FirstEntity> findByNameIn(List<String> names);
////
////    @Query(value = "{name: {$gt:?0, $lt:?1}}")
////    List<FirstEntity> findFirstEntitiesBetweenTwoNames(String startName, String endName);
////
////    @Query(value = "MATCH (firstEntity:FirstEntity {name:{0}}) <-- (secondEntity:SecondEntity) RETURN secondEntity")
////    List<Object> findSecondEntitiesOfSpecificFirstEntityName(String name);
//}
