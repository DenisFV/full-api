package ru.denisfv.fullapi.spring.upis.notbad;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class NotBadUPISApplication {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Не самый плохой вариант - инжектнуть бин с proxyMode TARGET_CLASS");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            ColorFrame bean = context.getBean(ColorFrame.class);
            bean.showOnRandomPlace();
            Thread.sleep(50);
        }
    }
}
