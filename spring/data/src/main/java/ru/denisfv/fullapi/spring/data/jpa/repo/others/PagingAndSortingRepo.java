package ru.denisfv.fullapi.spring.data.jpa.repo.others;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.denisfv.fullapi.spring.data.jpa.entity.FirstEntity;

public interface PagingAndSortingRepo extends PagingAndSortingRepository<FirstEntity, Long> {

    FirstEntity findByName(String name);
}
