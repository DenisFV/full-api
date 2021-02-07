package ru.denisfv.fullapi.spring.context.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XMLContextApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean(BeanInterface.class).beanMethod();
    }
}
