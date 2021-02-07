package ru.denisfv.fullapi.spring.dcb.conditional.dev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.denisfv.fullapi.spring.dcb.conditional.buisness.BeanClass;
import ru.denisfv.fullapi.spring.dcb.conditional.buisness.BeanDao;

@Repository
public class DevDao implements BeanDao {

    @Value("${name}")
    private String name;

    @Override
    public BeanClass getBeanClass() {
        BeanClass bean = new BeanClass();
        bean.setVal(name);
        return bean;
    }
}
