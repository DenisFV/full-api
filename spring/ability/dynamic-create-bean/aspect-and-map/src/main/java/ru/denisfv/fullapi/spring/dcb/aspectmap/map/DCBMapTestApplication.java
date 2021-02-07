package ru.denisfv.fullapi.spring.dcb.aspectmap.map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.denisfv.fullapi.spring.dcb.aspectmap.map.buisness.BeanServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DCBMapTestApplication {

    @Autowired
    private BeanServiceImpl service;

    @Test
    public void testGetDynamicBeanWithMap() {
        System.out.println("Dynamic Bean with Map: " + service.getDynamicBeanWithMap().getName());
    }
}
