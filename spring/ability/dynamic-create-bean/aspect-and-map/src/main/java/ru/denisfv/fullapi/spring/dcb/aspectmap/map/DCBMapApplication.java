package ru.denisfv.fullapi.spring.dcb.aspectmap.map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DCBMapApplication {

    public static void main(String[] args) {
        System.out.println("Создание бинов из проперти через аспекты");
        SpringApplication.run(DCBMapApplication.class, args);
    }
}
