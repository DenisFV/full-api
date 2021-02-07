package ru.denisfv.fullapi.spring.data.mongo.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document
public class FirstEntity {

    @Id
    String id;

    String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<SecondEntity> secondEntities;

    public FirstEntity(String name, Set<SecondEntity> secondEntities) {
        this.name = name;
        this.secondEntities = secondEntities;
    }
}
