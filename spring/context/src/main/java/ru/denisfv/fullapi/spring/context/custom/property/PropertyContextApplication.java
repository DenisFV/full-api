package ru.denisfv.fullapi.spring.context.custom.property;

public class PropertyContextApplication {

    public static void main(String[] args) {
        PropertyContextConfig context = new PropertyContextConfig("context.properties");
        context.getBean(SingletonBeanClass.class).beanMethod();
        context.getBean(PrototypeBeanClass.class).beanMethod();
    }
}
