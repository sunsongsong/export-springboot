package com.song.export.aspect;

import com.song.export.annotation.AspectCacheAnnotation;
import com.song.export.annotation.SongCacheAnnotation;
import com.song.export.cache.RedisService;
import com.song.export.model.common.JsonResult;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 使用aspect实现AOP 实现缓存的存储
 */
@Aspect
@Component
public class AspectCache {
    private static final Logger logger = LoggerFactory.getLogger(AspectCache.class);

    @Autowired
    RedisService redisService;

    /**
     * 环绕通知
     */
    //@Around(value = "execution(* com.song.export.web.AspectController.*(..)) && @annotation(annotation)")
    @Around(value="execution(* com.song.export.web.AspectController.*(..))")
    public Object aroundMethod(ProceedingJoinPoint jp) {
        // 获取切入的 Method
        MethodSignature joinPointObject = (MethodSignature) jp.getSignature();
        Method method = joinPointObject.getMethod();
        AspectCacheAnnotation annotation = method.getAnnotation(AspectCacheAnnotation.class);
        String methodName = joinPointObject.getName();
        try {
            String methodArgs = Arrays.asList(jp.getArgs()).toString(); //post请求参数是map同样可以
            long time = annotation.time();
            String className = jp.getTarget().getClass().getName();
            String redisKey = className + ":" + methodName + ":" + methodArgs;
            Object object = redisService.get(redisKey);
            if(null != object){
                System.out.println("前置通知");
                return object;
            }else{
                //执行目标方法
                object = jp.proceed();
                redisService.set(redisKey,object,time);
                System.out.println("返回通知");
                return object;
            }
        }catch (Throwable e) {
            System.out.println("异常通知" + e);
        }
        System.out.println("后置通知");
        return null;
    }


}
