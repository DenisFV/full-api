package ru.denisfv.fullapi.architecture.rsocket.server.router;

import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.TestMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.TestRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.DefaultTest;

class TestControllerTest extends DefaultTest<TestEntity, TestDto, TestRepo, TestMapper, Long> {

    TestControllerTest() {
        super("test",
                TestDto.builder()
                        .id(1L)
                        .value("value")
                        .build()
        );
    }
}