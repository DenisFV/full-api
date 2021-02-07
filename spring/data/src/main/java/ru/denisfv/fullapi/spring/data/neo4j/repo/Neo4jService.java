//package ru.denisfv.fullapi.spring.data.neo4j.repo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import reactor.core.publisher.Flux;
//import ru.denisfv.fullapi.spring.data.neo4j.entity.FirstEntity;
//import ru.denisfv.fullapi.spring.data.neo4j.entity.SecondEntity;
//
//import java.util.List;
//
//@Repository
//public class Neo4jService {
//
//    @Autowired
//    private Neo4jRepo repo;
//
//    public void save(List<FirstEntity> entities) {
//        repo.saveAll(entities);
//    }
//
//    public void deleteAll() {
//        repo.deleteAll();
//    }
//
//    public Flux<FirstEntity> findAll() {
//        return repo.findAll();
//    }
//
//    public FirstEntity findByName(String name) {
//        return repo.findByName(name);
//    }
//
//    public List<FirstEntity> findByNameLikeIgnoreCase(String likePattern) {
//        return null;//repo.findByNameLikeIgnoreCase(likePattern);
//    }
//
//    public List<String> findSecondEntityIdsWithSpecificSecondEntityNames(String partOfSecondEntityName) {
//        return null;//repo.findSecondEntityNamesWithSpecificSecondEntityNames(partOfSecondEntityName);
//    }
//
//    public List<FirstEntity> findByNameIn(List<String> names) {
//        return null;//repo.findByNameIn(names);
//    }
//
//    public List<FirstEntity> findFirstEntitiesBetweenTwoNames(String startName, String endName) {
//        return null;//repo.findFirstEntitiesBetweenTwoNames(startName, endName);
//    }
//
//    public List<SecondEntity> findSecondEntitiesOfSpecificFirstEntityName(String name) {
////        List<Object> list = repo.findSecondEntitiesOfSpecificFirstEntityName(name);
//        return null;
//    }
//}
