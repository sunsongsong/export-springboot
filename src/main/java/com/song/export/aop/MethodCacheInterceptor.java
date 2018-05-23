package com.song.export.aop;

import com.song.export.cache.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodCacheInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MethodCacheInterceptor.class);

    private static final String CACHE_NAME = "CHCHE_NAME";

    @Autowired
    private RedisService redisService;

    /**
     * 搭配 AspectJ 指示器“@annotation()”可以使本切面成为某个注解的代理实现
     * @return
     * @throws Throwable
     */
    /*@Around("@annotation(*.*.*))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{

        return null;
    }*/



}
