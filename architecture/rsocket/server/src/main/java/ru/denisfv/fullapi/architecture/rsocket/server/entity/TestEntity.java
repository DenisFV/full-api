package ru.denisfv.fullapi.architecture.rsocket.server.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = false, of = "id")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("test_table")
public class TestEntity extends AbstractEntity<Long> {

    @Id
    @Column("test_id")
    Long id;

    @NotBlank
    String value;
}
