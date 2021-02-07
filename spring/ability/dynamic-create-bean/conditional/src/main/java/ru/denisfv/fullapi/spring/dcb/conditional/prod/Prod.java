package ru.denisfv.fullapi.spring.dcb.conditional.prod;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(OnProdCondition.class)
public @interface Prod {
}
