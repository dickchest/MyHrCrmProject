package com.myhrcrmproject.configuration.log;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class LogConfiguration {

    @Pointcut("execution (public * com.myhrcrmproject.controller.*.*(..))")
    public void controllerLog() {}

    @Before("controllerLog()")
    public void beforeUsingAnyController(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("""
                        RECEIVED REQUEST:
                        IP : {}
                        URL : {}
                        HTTP METHOD : {}
                        CONTROLLER METHOD : {}.{}
                        """,
                request.getRemoteAddr(),
                request.getRequestURL().toString(),
                request.getMethod(),
                point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName());
    }

    @AfterThrowing(throwing = "e", pointcut = "controllerLog()")
    public void exceptionHandler(JoinPoint point, Exception e) {
        log.error("Exception situation by the reason: {}.{}",
                point.getSignature().getName(),
                e.getMessage());
    }
}
