package ru.denisfv.fullapi.architecture.rsocket.server.router;

import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestSecondEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.TestSecondMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.TestSecondRepo;
import ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.DefaultTest;

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