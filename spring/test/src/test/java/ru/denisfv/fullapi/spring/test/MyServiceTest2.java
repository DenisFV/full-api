package ru.denisfv.fullapi.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.denisfv.fullapi.spring.test.abstr.AbstractTest;
import ru.denisfv.fullapi.spring.test.service.MyService2;

import java.util.List;

class MyServiceTest2 extends AbstractTest {

    @Autowired
    MyService2 service;

    @Test
    void findAll() {
        List<String> all = service.findAll();
        System.out.println(all);
    }

    @Test
    void findById() {
        String byId = service.findById(1);
        System.out.println(byId);
    }
}