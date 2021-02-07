package ru.denisfv.fullapi.spring.data.jpa.repo.others;

import org.springframework.data.repository.Repository;
import ru.denisfv.fullapi.spring.data.jpa.entity.FirstEntity;

public interface EmptyRepo extends Repository<FirstEntity, Long> {

    FirstEntity findByName(String name);
}
