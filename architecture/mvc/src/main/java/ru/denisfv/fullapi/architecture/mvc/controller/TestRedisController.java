package ru.denisfv.fullapi.architecture.mvc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.mvc.controller.abstr.redis.AbstractRedisController;
import ru.denisfv.fullapi.architecture.mvc.controller.link.TestRedisLink;
import ru.denisfv.fullapi.architecture.mvc.dto.TestDto;
import ru.denisfv.fullapi.architecture.mvc.service.TestRedisService;

@RestController
@RequestMapping("/test-redis")
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "TestRedisController", description = "TestRedisController")
public class TestRedisController extends AbstractRedisController<TestDto, TestRedisService, Long,
        TestRedisController, TestRedisLink> {

    public TestRedisController(TestRedisService service, TestRedisLink link) {
        super(service, link);
    }
}