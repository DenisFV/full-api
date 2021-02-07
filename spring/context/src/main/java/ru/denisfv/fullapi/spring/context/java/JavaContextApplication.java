package ru.denisfv.fullapi.spring.context.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaContextApplication {

    public static void main(String[] args) {
        // начиная с версии 3 в спринге появились java конфигурации
        System.out.println("Поднимается контекст");
        System.out.println("ConfigurationClassPostProcessor добавляет новые BeanDefinition по @Bean из java-config");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        context.getBean(SingletonBeanClass.class).beanMethod();
        context.getBean(PrototypeBeanClass.class).beanMethod();
    }
}
