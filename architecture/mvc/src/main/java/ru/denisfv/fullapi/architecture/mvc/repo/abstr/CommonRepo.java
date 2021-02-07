package ru.denisfv.fullapi.architecture.mvc.repo.abstr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

@NoRepositoryBean
public interface CommonRepo<T extends AbstractEntity, K> extends JpaRepository<T, K> {
}