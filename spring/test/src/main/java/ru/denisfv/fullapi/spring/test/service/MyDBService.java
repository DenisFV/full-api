package ru.denisfv.fullapi.spring.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.spring.test.entity.MyEntity;
import ru.denisfv.fullapi.spring.test.repo.MyRepo;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class MyDBService {

    @Autowired
    private MyRepo repo;

    @PostConstruct
    public void init() {
        System.out.println("MyDBService created");
    }

    public List<MyEntity> findAll() {
        return repo.findAll();
    }

    public MyEntity findById(int id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public List<MyEntity> saveAll(List<MyEntity> entities) {
        return repo.saveAll(entities);
    }
}
