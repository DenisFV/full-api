package ru.denisfv.fullapi.architecture.mvc.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = false, of = "id")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "test_table", schema = "public")
public class TestEntity extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_id_seq")
    @SequenceGenerator(name = "test_id_seq", sequenceName = "public.test_id_seq", allocationSize = 1)
    @Column(name = "test_id")
    Long id;

    @NotBlank(message = "value должно быть задано")
    String value;
}
