package ru.denisfv.fullapi.architecture.mvc.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = false, of = "id")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "test_second_table", schema = "public")
public class TestSecondEntity extends AbstractEntity<String> {

    @Id
    @Column(name = "test_second_id")
    String id;

    @NotBlank(message = "value должно быть задано")
    String value;
}
