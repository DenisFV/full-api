package ru.denisfv.fullapi.architecture.mvc.controller.link;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import ru.denisfv.fullapi.architecture.mvc.controller.TestRedisController;
import ru.denisfv.fullapi.architecture.mvc.controller.link.abstr.DefaultRedisLink;
import ru.denisfv.fullapi.architecture.mvc.dto.TestDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestRedisLink extends DefaultRedisLink<TestDto, TestRedisController> {

    @Override
    public Link[] addLinks(TestDto entity, TestRedisController controller) {
        List<String> ignoreLinks = new ArrayList<>();

        List<Link> newLinks = new ArrayList<>();

        return super.addLinks(entity, controller, newLinks, ignoreLinks);
    }
}