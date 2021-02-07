package ru.denisfv.fullapi.spring.dcb.aspectmap.map.buisness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.denisfv.fullapi.spring.dcb.aspectmap.map.model.BeanInterface;

import java.util.Map;

@Service
@PropertySource("classpath:aspect.properties")
public class BeanServiceImpl {

    @Value("${dynamic.bean.name}")
    private String dynamicBeanName;

    @Autowired
    private Map<String, BeanInterface> dynamicBeanWithMap;

    public BeanInterface getDynamicBeanWithMap() {
        return this.dynamicBeanWithMap.get(dynamicBeanName);
    }
}
