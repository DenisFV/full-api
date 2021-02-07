package ru.denisfv.fullapi.architecture.mvc.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.architecture.mvc.dto.TestDto;
import ru.denisfv.fullapi.architecture.mvc.entity.TestEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.TestMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.TestRepo;
import ru.denisfv.fullapi.architecture.mvc.service.abstr.redis.AbstractRedisService;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestRedisService extends AbstractRedisService<TestEntity, TestDto, TestRepo, TestMapper, Long> {

    public TestRedisService(TestRepo repo) {
        super(repo);
    }
}