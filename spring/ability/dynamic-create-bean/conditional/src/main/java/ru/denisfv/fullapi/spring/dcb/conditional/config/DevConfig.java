package ru.denisfv.fullapi.spring.dcb.conditional.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.denisfv.fullapi.spring.dcb.conditional.dev.Dev;

@Dev
@ComponentScan(basePackages = "ru.denisfv.fullapi.spring.dcb.conditional.dev")
@PropertySource("classpath:dev.properties")
public class DevConfig {
}
