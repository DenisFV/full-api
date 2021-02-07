package ru.denisfv.fullapi.spring.context.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationContextApplication {

    public static void main(String[] args) {
        // начиная с версии 2.5 в спринге появились аннотации
        System.out.println("Поднимается контекст");
        System.out.println("ClassPathBeanDefinitionScanner создает дополнительные BeanDefinition для всех классов, помеченных @Component");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.denisfv.fullapi.spring.context.annotation");
        context.getBean(SingletonBeanClass.class).beanMethod();
        context.getBean(PrototypeBeanClass.class).beanMethod();
    }
}
