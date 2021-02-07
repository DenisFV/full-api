//package ru.denisfv.fullapi.spring.data.neo4j.entity;
//
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.data.neo4j.core.schema.GeneratedValue;
//import org.springframework.data.neo4j.core.schema.Id;
//import org.springframework.data.neo4j.core.schema.Node;
//
//@Data
//@NoArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Node
//public class SecondEntity {
//
//    @Id
//    @GeneratedValue
//    String id;
//    String name;
//
//    FirstEntity firstEntity;
//
//    public SecondEntity(String id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//}
