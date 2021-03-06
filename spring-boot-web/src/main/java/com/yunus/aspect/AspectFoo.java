package com.yunus.aspect;


import com.yunus.annotation.Login;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author lanxum
 */
@Aspect
@Component
public class AspectFoo {

    @Pointcut("@annotation(com.yunus.annotation.Login)")
    public void point() {

    }

    @Before("@annotation(login)")
    public void before(JoinPoint joinPoint, Login login) {
        System.out.println("切面类--before方法执行");
    }

    @After("point()")
    public void after(JoinPoint joinPoint) {
        System.out.println("切面类--after方法执行");
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        System.out.println("切面类--around方法执行结果为" + proceed);
    }

    @AfterReturning("point()")
    public void adterReturn(JoinPoint joinPoint) {
        System.out.println("切面类--adterReturn方法执行");
    }
}
