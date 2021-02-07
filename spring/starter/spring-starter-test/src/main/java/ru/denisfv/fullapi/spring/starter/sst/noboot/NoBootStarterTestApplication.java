package ru.denisfv.fullapi.spring.starter.sst.noboot;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class NoBootStarterTestApplication {

    public static void main(String[] args) {
        System.out.println("@Main отрабатвыает сразу после инит метода");
        new AnnotationConfigApplicationContext(TestConfig.class);
    }
}
