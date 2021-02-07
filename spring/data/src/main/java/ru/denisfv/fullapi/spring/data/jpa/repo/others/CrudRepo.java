package ru.denisfv.fullapi.spring.data.jpa.repo.others;

import org.springframework.data.repository.CrudRepository;
import ru.denisfv.fullapi.spring.data.jpa.entity.FirstEntity;

public interface CrudRepo extends CrudRepository<FirstEntity, Long> {

    FirstEntity findByName(String name);
}
