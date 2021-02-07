package ru.denisfv.fullapi.spring.data.jpa.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.denisfv.fullapi.spring.data.jpa.entity.FirstEntity;

import java.util.List;

@Repository
public class JpaService {

    @Autowired
    private JpaRepo repo;

    public void save(List<FirstEntity> entities) {
        System.out.println();
        repo.saveAll(entities);
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

    public List<Long> findSecondEntityIdsWithSpecificSecondEntityNames(String partOfSecondEntityName) {
        return repo.findSecondEntityIdsWithSpecificSecondEntityNames(partOfSecondEntityName);
    }

    public List<FirstEntity> findFirstEntitiesByIds(List<Long> ids) {
        return repo.findFirstEntitiesByIds(ids);
    }

    public List<FirstEntity> findFirstEntitiesBetweenTwoIds(Long startId, Long endId) {
        return repo.findFirstEntitiesBetweenTwoIds(startId, endId);
    }
}
