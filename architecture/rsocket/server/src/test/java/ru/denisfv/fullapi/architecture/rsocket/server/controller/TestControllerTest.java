package ru.denisfv.fullapi.architecture.rsocket.server.controller;

import ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr.DefaultTest;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.TestMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.TestRepo;

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