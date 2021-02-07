package ru.denisfv.fullapi.spring.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class MyService2 {

    @Value("${values2}")
    private String values;

    @PostConstruct
    public void init(){
        System.out.println("MyService2 created");
    }

    public List<String> findAll() {
        return Arrays.asList(values.split(";"));
    }

    public String findById(int id) {
        return Arrays.asList(values.split(";")).get(id);
    }
}
