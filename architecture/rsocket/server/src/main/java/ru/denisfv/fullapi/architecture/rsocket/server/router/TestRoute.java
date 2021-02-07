package ru.denisfv.fullapi.architecture.rsocket.server.router;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestDto;
import ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.AbstractRouter;
import ru.denisfv.fullapi.architecture.rsocket.server.service.TestService;

@RestController
@MessageMapping("test.")
@Slf4j
public class TestRoute extends AbstractRouter<TestDto, TestService, Long> {

    public TestRoute(TestService service) {
        super(service);
    }
}