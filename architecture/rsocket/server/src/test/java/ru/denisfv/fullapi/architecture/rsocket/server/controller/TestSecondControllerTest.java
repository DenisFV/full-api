package ru.denisfv.fullapi.architecture.rsocket.server.controller;

import ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr.DefaultTest;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestSecondEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.TestSecondMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.TestSecondRepo;

class TestSecondControllerTest extends DefaultTest<TestSecondEntity, TestSecondDto, TestSecondRepo, TestSecondMapper, String> {

    TestSecondControllerTest() {
        super("test-second",
                TestSecondDto.builder()
                        .id("1")
                        .value("value")
                        .build()
        );
    }
}