package ru.denisfv.fullapi.architecture.rsocket.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestSecondEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.TestSecondMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.TestSecondRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.AbstractService;

@Slf4j
@Service
public class TestSecondService extends AbstractService<TestSecondEntity, TestSecondDto, TestSecondRepo, TestSecondMapper, String> {

    public TestSecondService(TestSecondRepo repo) {
        super(repo);
    }
}