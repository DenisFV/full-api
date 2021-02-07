package ru.denisfv.fullapi.spring.dcb.conditional.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.denisfv.fullapi.spring.dcb.conditional.prod.Prod;

@Prod
@ComponentScan(basePackages = "ru.denisfv.fullapi.spring.dcb.conditional.prod")
@PropertySource("classpath:prod.properties")
public class ProdConfig {
}
