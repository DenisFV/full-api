package ru.denisfv.fullapi.spring.starter.sst.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootStarterTestApplication {

    public static void main(String[] args) {
        System.out.println("@Main отрабатвыает сразу после инит метода");
        SpringApplication.run(BootStarterTestApplication.class, args);
    }
}
