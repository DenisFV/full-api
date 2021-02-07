package ru.denisfv.fullapi.architecture.mvc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.mvc.controller.abstr.AbstractController;
import ru.denisfv.fullapi.architecture.mvc.controller.link.TestLink;
import ru.denisfv.fullapi.architecture.mvc.dto.TestDto;
import ru.denisfv.fullapi.architecture.mvc.service.TestService;

@RestController
@RequestMapping("/test")
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "TestController", description = "TestController")
public class TestController extends AbstractController<TestDto, TestService, Long, TestController, TestLink> {

    public TestController(TestService service, TestLink link) {
        super(service, link);
    }
}