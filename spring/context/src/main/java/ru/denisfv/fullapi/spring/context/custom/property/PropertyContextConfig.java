package ru.denisfv.fullapi.spring.context.custom.property;

import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class PropertyContextConfig extends GenericApplicationContext {

    public PropertyContextConfig(String filename) {
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(this);
        int countBean = reader.loadBeanDefinitions(filename);
        System.out.println("Найдено бинов: " + countBean);
        refresh();
    }
}
