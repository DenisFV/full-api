package ru.denisfv.fullapi.spring.data.nodata.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class FirstEntity {

    @Id
    @GeneratedValue
    Long id;

    String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<SecondEntity> secondEntities;

    public FirstEntity(String name, Set<SecondEntity> secondEntities) {
        this.name = name;
        this.secondEntities = secondEntities;
    }
}
