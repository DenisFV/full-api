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

@EqualsAndHashCode(callSuper = false, of = "id")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("test_second_table")
public class TestSecondEntity extends AbstractEntity<String> {

    @Id
    @Column("test_second_id")
    String id;

    @NotBlank
    String value;
}
