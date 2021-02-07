//package ru.denisfv.fullapi.spring.data.neo4j.entity;
//
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.data.neo4j.core.schema.*;
//import org.springframework.data.neo4j.core.schema.Relationship.Direction;
//
//import java.util.Set;
//
//@Data
//@NoArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Node("First")
//public class FirstEntity {
//
//    @Id
//    @GeneratedValue
//    String id;
//
//    @Property("name")
//    String name;
//
//    //BOTH надо
//    @Relationship(direction = Direction.INCOMING)
//    Set<SecondEntity> secondEntities;
//
//    public FirstEntity(String id, String name, Set<SecondEntity> secondEntities) {
//        this.id = id;
//        this.name = name;
//        this.secondEntities = secondEntities;
//    }
//}
