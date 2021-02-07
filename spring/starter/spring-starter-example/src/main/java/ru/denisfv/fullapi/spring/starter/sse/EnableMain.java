package ru.denisfv.fullapi.spring.starter.sse;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(StarterConfig.class)
public @interface EnableMain {
}
