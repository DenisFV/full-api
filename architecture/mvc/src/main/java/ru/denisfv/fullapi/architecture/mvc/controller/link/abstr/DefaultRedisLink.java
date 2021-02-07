package ru.denisfv.fullapi.architecture.mvc.controller.link.abstr;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import ru.denisfv.fullapi.architecture.mvc.controller.abstr.redis.AbstractRedisController;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
public abstract class DefaultRedisLink<D extends AbstractEntity, C extends AbstractRedisController> extends DefaultLink<D, C>
        implements RepresentationModelAssembler<D, EntityModel<D>> {

    @Override
    public Link[] addLinks(D entity, C controller) {
        Class<? extends AbstractRedisController> clazz = controller.getClass();
        String id = Objects.nonNull(entity.getId()) ? String.valueOf(entity.getId()) : "1";

        List<Link> newLinks = new ArrayList<>();

        newLinks.add(linkTo(methodOn(clazz).findById(id))
                .withRel("flushById")
                .withType(HttpMethod.GET.name())
                .withTitle("кеша от записи с id"));

        newLinks.add(linkTo(methodOn(clazz).findById(id))
                .withRel("flushAll")
                .withType(HttpMethod.GET.name())
                .withTitle("Очистка кеша от всех записей"));

        newLinks.add(linkTo(methodOn(clazz).findById(id))
                .withRel("findAllKeys")
                .withType(HttpMethod.GET.name())
                .withTitle("Поиск всех ключей кеша"));

        newLinks.add(linkTo(methodOn(clazz).findById(id))
                .withRel("findAllCache")
                .withType(HttpMethod.GET.name())
                .withTitle("Поиск всех объектов кеша"));

        newLinks.add(linkTo(methodOn(clazz).findById(id))
                .withRel("getTtlById")
                .withType(HttpMethod.GET.name())
                .withTitle("Поиск оставшегося времени жизни ключа кеша"));

        return super.addLinks(entity, controller, newLinks, Collections.emptyList());
    }
}
