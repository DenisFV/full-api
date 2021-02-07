package ru.denisfv.fullapi.spring.data.nodata.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SecondEntity {

    @Id
    @GeneratedValue
    Long id;
    String name;

    public SecondEntity(String name) {
        this.name = name;
    }
}
