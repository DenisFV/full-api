package ru.denisfv.fullapi.architecture.rsocket.server.router;

import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.TestMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.TestRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.DefaultRedisTest;

class TestRedisControllerTest extends DefaultRedisTest<TestEntity, TestDto, TestRepo, TestMapper, Long> {

    TestRedisControllerTest() {
        super("test-redis",
                TestDto.builder()
                        .id(1L)
                        .value("value")
                        .build()
        );
    }
}