package ru.denisfv.fullapi.spring.context.xml;

public class BeanClass implements BeanInterface {

    private String beanField;

    public void setBeanField(String beanField) {
        this.beanField = beanField;
    }

    @Override
    public void beanMethod() {
        System.out.println("beanMethod()");
    }
}
