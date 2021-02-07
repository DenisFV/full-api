package ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;

@NoRepositoryBean
public interface CommonRepo<T extends AbstractEntity, K> extends ReactiveCrudRepository<T, K> {
}