package ru.denisfv.fullapi.architecture.mvc;

import ru.denisfv.fullapi.architecture.mvc.abstr.DefaultRedisTest;
import ru.denisfv.fullapi.architecture.mvc.dto.TestDto;
import ru.denisfv.fullapi.architecture.mvc.entity.TestEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.TestMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.TestRepo;

class TestRedisControllerTest extends DefaultRedisTest<TestEntity, TestDto, TestRepo, TestMapper, Long> {

    private static final String url = "/test-redis";
    private static final TestDto dto = TestDto.builder()
            .id(1L)
            .value("value")
            .build();

    TestRedisControllerTest() {
        super(url, dto);
    }
}