package ru.denisfv.fullapi.spring.dcb.conditional.dev;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(OnDevCondition.class)
public @interface Dev {
}
