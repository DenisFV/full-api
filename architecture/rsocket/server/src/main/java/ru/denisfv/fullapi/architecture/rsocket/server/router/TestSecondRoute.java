package ru.denisfv.fullapi.architecture.rsocket.server.router;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.AbstractRouter;
import ru.denisfv.fullapi.architecture.rsocket.server.service.TestSecondService;

@RestController
@MessageMapping("test-second.")
@Slf4j
public class TestSecondRoute extends AbstractRouter<TestSecondDto, TestSecondService, String> {

    public TestSecondRoute(TestSecondService service) {
        super(service);
    }
}