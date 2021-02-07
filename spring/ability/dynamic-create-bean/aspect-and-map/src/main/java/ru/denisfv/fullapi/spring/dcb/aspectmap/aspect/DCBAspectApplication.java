package ru.denisfv.fullapi.spring.dcb.aspectmap.aspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DCBAspectApplication {

    public static void main(String[] args) {
        System.out.println("Создание бинов из проперти через аспекты");
        SpringApplication.run(DCBAspectApplication.class, args);
    }
}
