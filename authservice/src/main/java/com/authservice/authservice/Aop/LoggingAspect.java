package com.authservice.authservice.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    
     @Before("execution(* com.authservice.authservice.authController..*.*(..)) || " +
        "execution(* com.authservice.authservice.authService..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[LOG] Entering: " + joinPoint.getSignature());
    }

    @AfterReturning(value = 
    "execution(* com.authservice.authservice.authController..*.*(..)) || " +
            "execution(* com.authservice.authservice.authService..*.*(..))",
            returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println("[LOG] Exiting: " + joinPoint.getSignature() +
                " | Return: " + result);
    }
}
