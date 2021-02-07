package ru.denisfv.fullapi.architecture.rsocket.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.TestMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.TestRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.AbstractService;

@Slf4j
@Service
public class TestService extends AbstractService<TestEntity, TestDto, TestRepo, TestMapper, Long> {

    public TestService(TestRepo repo) {
        super(repo);
    }
}