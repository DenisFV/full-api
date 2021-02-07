package ru.denisfv.fullapi.architecture.rsocket.client;

import ru.denisfv.fullapi.architecture.rsocket.client.abstr.DefaultTest;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.TestDto;

class TestControllerTest extends DefaultTest<TestDto, Long> {

    TestControllerTest() {
        super("test",
                TestDto.builder()
                        .id(1L)
                        .value("value")
                        .build()
        );
    }
}