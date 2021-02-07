package ru.denisfv.fullapi.architecture.mvc;

import ru.denisfv.fullapi.architecture.mvc.abstr.DefaultTest;
import ru.denisfv.fullapi.architecture.mvc.dto.TestDto;
import ru.denisfv.fullapi.architecture.mvc.entity.TestEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.TestMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.TestRepo;

class TestControllerTest extends DefaultTest<TestEntity, TestDto, TestRepo, TestMapper, Long> {

    private static final String url = "/test";
    private static final TestDto dto = TestDto.builder()
            .id(1L)
            .value("value")
            .build();

    TestControllerTest() {
        super(url, dto);
    }
}