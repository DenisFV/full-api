package ru.denisfv.fullapi.spring.upis.good;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GoodUPISApplication {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Хороший вариант - сделать абстрактный метод, возвращающий бин");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            ColorFrame bean = context.getBean(ColorFrame.class);
            bean.showOnRandomPlace();
            Thread.sleep(50);
        }
    }
}
