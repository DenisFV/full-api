package ru.denisfv.fullapi.spring.lifecycle;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.denisfv.fullapi.spring.lifecycle.model.BeanInterface;

public class SpringLifeCycleApplication {

    public static boolean isCGLIB
            = false
            ;

    public static void main(String[] args) {

        System.out.print("// Жизненый цикл спринга на примере проксирования через ");
        if (isCGLIB) {
            System.out.print("CGLIB");
        } else {
            System.out.print("DynamicProxy");
        }
        System.out.println(" //\n");

        System.out.println("1. Поднимается контекст");
        System.out.println("2. Создается BeanFactory");
        System.out.println("3. XmlBeanDefinitionReader начинает парсинг xml, проходится по всем классам и кладет инфу о них в BeanDefinition");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        System.out.println("12. Все бины полностью настроены и складываются в контейнер (Ioc)");

        System.out.println("13. Достается бин из контекста и вызывается его метод");
        context.getBean(BeanInterface.class).beanMethod();

        System.out.println("14. Контекст закрывается");
    }
}
