package ru.denisfv.fullapi.spring.data.jpa.repo.others;

import org.springframework.data.repository.RepositoryDefinition;
import ru.denisfv.fullapi.spring.data.jpa.entity.FirstEntity;

@RepositoryDefinition(domainClass = FirstEntity.class, idClass = Long.class)
public interface CustomRepo {

    FirstEntity findByName(String name);
}
