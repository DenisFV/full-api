package ru.denisfv.fullapi.architecture.mvc.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

//    @Around("execution(* ru.denisfv.fullapi.architecture.service.*.*(..)) || execution(* ru.denisfv.fullapi.architecture.service.abstr.*.*(..))")
//    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object returnValue = joinPoint.proceed();
//        log.info("объект на выходе:\n {}", returnValue);
//        return returnValue;
//    }
}