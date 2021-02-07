package ru.denisfv.fullapi.architecture.mvc.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import java.io.Serializable;
import java.util.Optional;

/**
 * Класс заглушка возвращаемого значения для Swagger
 */
@Data
@Tag(name = "ResponseEntityHelper", description = "Класс заглушка возвращаемого значения для Swagger")
public class ResponseEntityHelper<T extends AbstractEntity> implements Serializable {

    public ResponseEntityHelper(final Integer start, final Integer limit, final String sort, final Long total,
                                final CollectionModel<EntityModel<T>> data) {
        this.start = start;
        this.limit = limit;
        this.sort = sort;
        this.total = total;
        this.data = data;
        Optional.ofNullable(data)
                .ifPresent(e -> e.getContent()
                        .stream()
                        .findFirst()
                        .ifPresent(k -> this.entity = k.getContent()));
    }

    @JsonIgnore
    T entity;

    @Schema(description = "Начальный индекс элемента", example = "0")
    Integer start;

    @Schema(description = "Максимальное кол-во элементов", example = "500")
    Integer limit;

    @Schema(description = "Сортировка", example = "sort, sort-")
    String sort;

    @Schema(description = "Всего доступных элементов", example = "1")
    Long total;

    @Schema(description = "Список объектов")
    CollectionModel<EntityModel<T>> data;
}
