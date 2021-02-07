package ru.denisfv.fullapi.spring.scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomScopeApplication {

    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        while (true) {
            context.getBean(BeanClass.class).printBeanField();
            Thread.sleep(1000);
        }
    }
}
