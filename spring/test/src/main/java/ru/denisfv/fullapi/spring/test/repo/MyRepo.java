package ru.denisfv.fullapi.spring.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denisfv.fullapi.spring.test.entity.MyEntity;

@Repository
public interface MyRepo extends JpaRepository<MyEntity, Integer> {
}
