package ru.denisfv.fullapi.architecture.rsocket.client.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.rsocket.client.controller.abstr.redis.AbstractRedisController;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.TestDto;

@RestController
@RequestMapping("test-redis")
@Slf4j
@Tag(name = "TestRedisController", description = "TestRedisController")
public class TestRedisController extends AbstractRedisController<TestDto, Long> {

    public TestRedisController() {
        super();
    }
}