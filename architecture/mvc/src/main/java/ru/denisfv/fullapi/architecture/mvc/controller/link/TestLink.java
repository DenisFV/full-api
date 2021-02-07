package ru.denisfv.fullapi.architecture.mvc.controller.link;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import ru.denisfv.fullapi.architecture.mvc.controller.TestController;
import ru.denisfv.fullapi.architecture.mvc.controller.link.abstr.DefaultLink;
import ru.denisfv.fullapi.architecture.mvc.dto.TestDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestLink extends DefaultLink<TestDto, TestController> {

    @Override
    public Link[] addLinks(TestDto entity, TestController controller) {
        List<String> ignoreLinks = new ArrayList<>();

        List<Link> newLinks = new ArrayList<>();

        return super.addLinks(entity, controller, newLinks, ignoreLinks);
    }
}