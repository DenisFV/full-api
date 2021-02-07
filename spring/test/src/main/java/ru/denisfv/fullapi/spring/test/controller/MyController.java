package ru.denisfv.fullapi.spring.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.denisfv.fullapi.spring.test.entity.MyEntity;
import ru.denisfv.fullapi.spring.test.service.MyDBService;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class MyController {

    @Autowired
    MyDBService service;

    @PostConstruct
    public void init() {
        System.out.println("MyController created");
    }

    @GetMapping
    public ResponseEntity<List<MyEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
