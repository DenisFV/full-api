package ru.denisfv.fullapi.architecture.mvc.controller.link.abstr;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import ru.denisfv.fullapi.architecture.mvc.controller.abstr.AbstractController;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.util.ResponseEntityHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
public abstract class DefaultLink<T extends AbstractEntity, C extends AbstractController> implements RepresentationModelAssembler<T, EntityModel<T>> {

    @Override
    public EntityModel<T> toModel(final T entity) {
        return null;
    }

    public EntityModel<T> toModel(final T entity, final C controller) {
        return EntityModel.of(entity, addLinks(entity, controller, Collections.emptyList(), Collections.emptyList()));
    }

    public EntityModel<ResponseEntityHelper<T>> toModel(final ResponseEntityHelper<T> helper, final T entity, final C controller) {
        return EntityModel.of(helper, addLinks(entity, controller, Collections.emptyList(), Collections.emptyList()));
    }

    public CollectionModel<EntityModel<T>> toCollectionModel(final Iterable<? extends T> entities, final C controller) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(e -> toModel(e, controller))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), (x$0) -> CollectionModel.of(x$0, new Link[0]))
                );
    }

    public Link[] addLinks(final T entity, final C controller) {
        return addLinks(entity, controller, Collections.emptyList(), Collections.emptyList());
    }

    @SuppressWarnings("unchecked")
    protected Link[] addLinks(final T entity, final C controller, final List<Link> linkList, final List<String> ignoreLinks) {
        List<Link> links = new ArrayList<>(linkList);
        Class<? extends AbstractController> clazz = controller.getClass();
        String id = Objects.nonNull(entity.getId()) ? String.valueOf(entity.getId()) : "1";

        links.add(linkTo(methodOn(clazz).head(id))
                .withRel("head")
                .withType(HttpMethod.HEAD.name())
                .withTitle("Существование обекта"));

        links.add(linkTo(methodOn(clazz).findById(id))
                .withRel("findById")
                .withType(HttpMethod.GET.name())
                .withTitle("Поиск обекта по ИД"));

        links.add(linkTo(methodOn(clazz).findAll())
                .withRel("findAll")
                .withType(HttpMethod.GET.name())
                .withTitle("Поиск всех объектов"));

        links.add(linkTo(methodOn(clazz).create(entity))
                .withRel("create")
                .withType(HttpMethod.POST.name())
                .withTitle("Создание обекта"));

        links.add(linkTo(methodOn(clazz).update(entity))
                .withRel("update")
                .withType(HttpMethod.PUT.name())
                .withTitle("Обновление обекта"));

        links.add(linkTo(methodOn(clazz).deleteById(id))
                .withRel("deleteById")
                .withType(HttpMethod.DELETE.name())
                .withTitle("Удалить обект"));

        links.add(linkTo(methodOn(clazz).deleteAll())
                .withRel("deleteAll")
                .withType(HttpMethod.DELETE.name())
                .withTitle("Удалить все обекты"));

        links.add(linkTo(methodOn(clazz).find(null, null, null, null))
                .withRel("find")
                .withType(HttpMethod.GET.name())
                .withTitle("Возвращает список элементов в соответствии с параметрами"));

        links = links.stream().filter(e -> ignoreLinks.contains(e.getName())).collect(Collectors.toList());

        String self = getMethodName();
        return links.stream()
                .map(e -> e.getRel().value().equals(self) ? e.withSelfRel() : e)
                .toArray(Link[]::new);
    }

    private String getMethodName() {
        return Objects.requireNonNull(Stream.of(new Throwable().getStackTrace())
                .filter(e -> e.getClassName().contains(".controller."))
                .reduce((f, s) -> s).orElse(null))
                .getMethodName();
    }
}
