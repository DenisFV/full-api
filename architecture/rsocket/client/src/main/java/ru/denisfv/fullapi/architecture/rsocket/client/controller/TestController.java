package ru.denisfv.fullapi.architecture.rsocket.client.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.rsocket.client.controller.abstr.AbstractController;
import ru.denisfv.fullapi.architecture.rsocket.client.dto.TestDto;

@RestController
@RequestMapping("test")
@Slf4j
@Tag(name = "TestController", description = "TestController")
public class TestController extends AbstractController<TestDto, Long> {

    public TestController() {
        super();
    }
}