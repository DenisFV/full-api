package ru.denisfv.fullapi.spring.dcb.conditional.prod;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnProdCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            return System.getenv("COND").equals("PROD");
        } catch (NullPointerException e) {
            throw new NullPointerException("Не указана переменная COND");
        }
    }
}
