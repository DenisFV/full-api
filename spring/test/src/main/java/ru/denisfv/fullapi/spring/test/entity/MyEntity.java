package ru.denisfv.fullapi.spring.test.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "my_entity")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyEntity {

    @Id
    @GeneratedValue
    int id;
    String value;

    public MyEntity(String value) {
        this.value = value;
    }
}
