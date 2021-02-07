package ru.denisfv.fullapi.architecture.mvc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.architecture.mvc.controller.abstr.AbstractController;
import ru.denisfv.fullapi.architecture.mvc.controller.link.TestSecondLink;
import ru.denisfv.fullapi.architecture.mvc.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.mvc.service.TestSecondService;

@RestController
@RequestMapping("/test-second")
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "TestSecondController", description = "TestSecondController")
public class TestSecondController extends AbstractController<TestSecondDto, TestSecondService, String,
        TestSecondController, TestSecondLink> {

    public TestSecondController(TestSecondService service, TestSecondLink link) {
        super(service, link);
    }
}