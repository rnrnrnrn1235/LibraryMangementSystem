package com.ghada.library.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogginAspect {

    @Before("execution(* com.ghada.library.libraryService..*.*(..)) || execution(* com.ghada.library.libraryController..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[LOG] Entering: " + joinPoint.getSignature());
    }

    @AfterReturning(value = ("execution(* com.ghada.library.libraryService..*.*(..)) || execution(* com.ghada.library.libraryController..*.*(..))"), returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println("[LOG] Exiting: " + joinPoint.getSignature() +
                " | Return: " + result);
    }
}
