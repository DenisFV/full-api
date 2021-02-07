package ru.denisfv.fullapi.architecture.rsocket.server.router;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestDto;
import ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.redis.AbstractRedisRouter;
import ru.denisfv.fullapi.architecture.rsocket.server.service.TestRedisService;

@RestController
@MessageMapping("test-redis.")
@Slf4j
public class TestRedisRouter extends AbstractRedisRouter<TestDto, TestRedisService, Long> {

    public TestRedisRouter(TestRedisService service) {
        super(service);
    }
}