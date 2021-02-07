package ru.denisfv.fullapi.architecture.rsocket.client;

import ru.denisfv.fullapi.architecture.rsocket.client.abstr.DefaultTest;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.TestSecondDto;

class TestSecondControllerTest extends DefaultTest<TestSecondDto, String> {

    TestSecondControllerTest() {
        super("test-second",
                TestSecondDto.builder()
                        .id("1")
                        .value("value")
                        .build()
        );
    }
}