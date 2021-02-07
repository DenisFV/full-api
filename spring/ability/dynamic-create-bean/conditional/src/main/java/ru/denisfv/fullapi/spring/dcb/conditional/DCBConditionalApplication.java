package ru.denisfv.fullapi.spring.dcb.conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DCBConditionalApplication {

    public static void main(String[] args) {
        System.out.println("Создание бинов в зависимсоти от профиля через @Conditional");
        // требуется создать системную переменную COND, со значением PROD или DEV
        new AnnotationConfigApplicationContext("ru.denisfv.fullapi.spring.dcb.conditional.config");
    }
}
