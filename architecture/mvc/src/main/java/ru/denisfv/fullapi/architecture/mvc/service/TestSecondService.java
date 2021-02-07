package ru.denisfv.fullapi.architecture.mvc.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.architecture.mvc.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.mvc.entity.TestSecondEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.TestSecondMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.TestSecondRepo;
import ru.denisfv.fullapi.architecture.mvc.service.abstr.AbstractService;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestSecondService extends AbstractService<TestSecondEntity, TestSecondDto, TestSecondRepo, TestSecondMapper, String> {

    public TestSecondService(TestSecondRepo repo) {
        super(repo);
    }
}