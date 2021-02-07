package ru.denisfv.fullapi.spring.dcb.conditional.buisness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BeanServiceImpl implements BeanService {

    @Autowired
    private BeanDao dao;

    @Override
    @PostConstruct
    public void method() {
        BeanClass bean = dao.getBeanClass();
        System.out.println(bean.getVal());
    }
}
