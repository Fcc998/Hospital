package com.ming.hospital.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* com.ming.hospital.service.impl.*.*(..))")
    public void servicePointcut() {}

    @Around("servicePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("执行: " + className + "." + methodName + " - 参数: " + Arrays.toString(args));
        
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.info("完成: " + className + "." + methodName + " - 耗时: " + (endTime - startTime) + "ms");
            return result;
        } catch (Exception e) {
            logger.severe("异常: " + className + "." + methodName + " - 错误: " + e.getMessage());
            throw e;
        }
    }
}
