package com.imooc.girl.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {
    private final  static Logger logger=LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.imooc.girl.controller.GirlController.*(..))")
    public void log(){}
    @Before("log())")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //获取url
        logger.info("url="+request.getRequestURL());
        //获取method
        logger.info("method="+request.getMethod());
        //获取IP
        logger.info("ip="+request.getRemoteAddr());
        //获取类方法
        logger.info("class_method="+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        //获取参数
        logger.info("arg="+joinPoint.getArgs());
    }
    @After("log()")
    public void doAfter(){
        logger.info("after");
    }
    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturning(Object object){
        logger.info("response="+object);
    }
}
