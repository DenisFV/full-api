package ru.denisfv.fullapi.spring.upis.bad;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BadUPISApplication {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Плохой вариант - инжекнуть контекст спринга в бин");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            ColorFrame bean = context.getBean(ColorFrame.class);
            bean.showOnRandomPlace();
            Thread.sleep(50);
        }
    }
}
