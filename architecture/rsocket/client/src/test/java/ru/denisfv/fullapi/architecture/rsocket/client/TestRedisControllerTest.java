package ru.denisfv.fullapi.architecture.rsocket.client;

import ru.denisfv.fullapi.architecture.rsocket.client.abstr.DefaultRedisTest;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.TestDto;

class TestRedisControllerTest extends DefaultRedisTest<TestDto, Long> {

    TestRedisControllerTest() {
        super("test-redis",
                TestDto.builder()
                        .id(1L)
                        .value("value")
                        .build()
        );
    }
}