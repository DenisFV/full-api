package ru.denisfv.fullapi.architecture.mvc;

import ru.denisfv.fullapi.architecture.mvc.abstr.DefaultTest;
import ru.denisfv.fullapi.architecture.mvc.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.mvc.entity.TestSecondEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.TestSecondMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.TestSecondRepo;

class TestSecondControllerTest extends DefaultTest<TestSecondEntity, TestSecondDto, TestSecondRepo, TestSecondMapper, String> {

    private static final String url = "/test-second";
    private static final TestSecondDto dto = TestSecondDto.builder()
            .id("1")
            .value("value")
            .build();

    TestSecondControllerTest() {
        super(url, dto);
    }
}