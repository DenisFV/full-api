package ru.denisfv.fullapi.spring.data.nodata.repo;

import org.springframework.stereotype.Repository;
import ru.denisfv.fullapi.spring.data.nodata.entity.FirstEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class NoDataRepo {

    @PersistenceContext
    private EntityManager em;

    public FirstEntity findByName(String name) {
        Query query = em.createQuery("select s from FirstEntity as s where s.name = :name");
        return (FirstEntity) query.setParameter("name", name).getSingleResult();
    }

    @SuppressWarnings(value = "unchecked")
    public List<FirstEntity> findAll() {
        Query query = em.createQuery("select s from FirstEntity as s");
        return query.getResultList();
    }

    public void save(List<FirstEntity> entities) {
        for (FirstEntity entity : entities) {
            em.persist(entity);
        }
    }
}
