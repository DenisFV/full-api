package ru.denisfv.fullapi.spring.dcb.aspectmap.aspect.buisness;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.spring.dcb.aspectmap.aspect.model.BeanInterface;

@Service
@PropertySource("classpath:aspect.properties")
public class BeanServiceImpl {

    @Value("${dynamic.bean.name}")
    private String dynamicBeanName;

    private BeanInterface dynamicBeanWithAspect;

    @InjectDynamicBean
    public BeanInterface getDynamicBeanWithAspect() {
        return this.dynamicBeanWithAspect;
    }
}
