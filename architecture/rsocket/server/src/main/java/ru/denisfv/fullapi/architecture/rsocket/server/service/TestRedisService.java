package ru.denisfv.fullapi.architecture.rsocket.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.TestMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.TestRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.redis.AbstractRedisService;

@Slf4j
@Service
public class TestRedisService extends AbstractRedisService<TestEntity, TestDto, TestRepo, TestMapper, Long> {

    public TestRedisService(TestRepo repo) {
        super(repo);
    }
}