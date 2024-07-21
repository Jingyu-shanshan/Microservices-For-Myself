package com.microservices.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Around("@annotation(com.microservices.config.aop.LogExecutionTime)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("Starting execution method {} at {}", methodName, simpleDateFormat.format(new Date()));

        Object result = joinPoint.proceed();

        log.info("Finished execution method {} at {}", methodName, simpleDateFormat.format(new Date()));
        return result;
    }
}
