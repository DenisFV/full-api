package ru.denisfv.fullapi.spring.dcb.conditional.dev;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnDevCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) throws RuntimeException {
        try {
            return System.getenv("COND").equals("DEV");
        } catch (NullPointerException e) {
            throw new NullPointerException("Не указана переменная COND");
        }
    }
}
