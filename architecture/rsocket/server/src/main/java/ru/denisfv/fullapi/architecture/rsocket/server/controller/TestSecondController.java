package ru.denisfv.fullapi.architecture.rsocket.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.rsocket.server.controller.abstr.AbstractController;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestSecondDto;

@RestController
@RequestMapping("test-second")
@Slf4j
@Tag(name = "TestSecondController", description = "TestSecondController")
public class TestSecondController extends AbstractController<TestSecondDto, String> {

    public TestSecondController() {
        super();
    }
}