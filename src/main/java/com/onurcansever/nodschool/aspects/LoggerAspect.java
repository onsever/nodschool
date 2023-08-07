package com.onurcansever.nodschool.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Around("execution(* com.onurcansever.nodschool.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        // Before
        log.info(joinPoint.getSignature().toString() + " method execution has been started...");
        Instant startTime = Instant.now();

        // Invoking the method
        Object object = joinPoint.proceed();

        // After
        Instant finishTime = Instant.now();
        long timeElapsed = Duration.between(startTime, finishTime).toMillis();
        log.info("Time took to execute: " + joinPoint.getSignature().toString() + " method is: " + timeElapsed);
        log.info(joinPoint.getSignature().toString() + " method execution has been finished...");

        return object;
    }

    @AfterThrowing(value = "execution(* com.onurcansever.nodschool.*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.error(joinPoint.getSignature() + " An exception happened due to: " + exception.getMessage());
    }

}
