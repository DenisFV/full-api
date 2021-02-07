package ru.denisfv.fullapi.spring.data.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.denisfv.fullapi.spring.data.jpa.entity.FirstEntity;

import java.util.List;

public interface JpaRepo extends JpaRepository<FirstEntity, Long> {

    FirstEntity findByName(String name);

    List<FirstEntity> findByNameLikeIgnoreCase(String likePattern);

    @Query(value = "select s.id from FirstEntity f" +
            " join f.secondEntities s" +
            " where s.name like :pattern")
    List<Long> findSecondEntityIdsWithSpecificSecondEntityNames(@Param("pattern") String partOfSecondEntityName);

    @Query(value = "select f from FirstEntity f where f.id in :ids")
    List<FirstEntity> findFirstEntitiesByIds(@Param("ids") List<Long> ids);

    @Query(nativeQuery = true, value = "select * from PUBLIC.FIRSTENTITY f where f.id between :st and :en")
    List<FirstEntity> findFirstEntitiesBetweenTwoIds(@Param("st") Long startId, @Param("en") Long endId);
}
