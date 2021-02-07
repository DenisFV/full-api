package ru.denisfv.fullapi.spring.data.mongo.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.denisfv.fullapi.spring.data.mongo.entity.FirstEntity;
import ru.denisfv.fullapi.spring.data.mongo.entity.SecondEntity;

import java.util.List;

@Repository
public class MongoService {

    @Autowired
    private MongoRepo repo;

    public void save(List<FirstEntity> entities) {
        repo.saveAll(entities);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public List<FirstEntity> findAll() {
        return repo.findAll();
    }

    public FirstEntity findByName(String name) {
        return repo.findByName(name);
    }

    public List<FirstEntity> findByNameLikeIgnoreCase(String likePattern) {
        return repo.findByNameLikeIgnoreCase(likePattern);
    }

    public List<String> findSecondEntityIdsWithSpecificSecondEntityNames(String partOfSecondEntityName) {
        return repo.findSecondEntityNamesWithSpecificSecondEntityNames(partOfSecondEntityName);
    }

    public List<FirstEntity> findByNameIn(List<String> names) {
        return repo.findByNameIn(names);
    }

    public List<FirstEntity> findFirstEntitiesBetweenTwoNames(String startName, String endName) {
        return repo.findFirstEntitiesBetweenTwoNames(startName, endName);
    }
}
