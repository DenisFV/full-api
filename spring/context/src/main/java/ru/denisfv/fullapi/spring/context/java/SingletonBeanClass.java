package ru.denisfv.fullapi.spring.context.java;

public class SingletonBeanClass implements BeanInterface {

    private String beanField;

    public void setBeanField(String beanField) {
        this.beanField = beanField;
    }

    @Override
    public void beanMethod() {
        System.out.println("beanMethod() in singleton bean");
    }
}
