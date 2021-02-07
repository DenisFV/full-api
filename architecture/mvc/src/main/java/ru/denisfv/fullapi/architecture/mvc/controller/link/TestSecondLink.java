package ru.denisfv.fullapi.architecture.mvc.controller.link;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import ru.denisfv.fullapi.architecture.mvc.controller.TestSecondController;
import ru.denisfv.fullapi.architecture.mvc.controller.link.abstr.DefaultLink;
import ru.denisfv.fullapi.architecture.mvc.dto.TestSecondDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestSecondLink extends DefaultLink<TestSecondDto, TestSecondController> {

    @Override
    public Link[] addLinks(TestSecondDto entity, TestSecondController controller) {
        List<String> ignoreLinks = new ArrayList<>();

        List<Link> newLinks = new ArrayList<>();

        return super.addLinks(entity, controller, newLinks, ignoreLinks);
    }
}