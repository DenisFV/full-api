package ru.denisfv.fullapi.spring.dcb.aspectmap.aspect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.denisfv.fullapi.spring.dcb.aspectmap.aspect.buisness.BeanServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DCBAspectTestApplication {

    @Autowired
    private BeanServiceImpl service;

    @Test
    public void testGetDynamicBeanWithAspect() {
        System.out.println("Dynamic Bean with Aspect: " + service.getDynamicBeanWithAspect().getName());
    }
}
