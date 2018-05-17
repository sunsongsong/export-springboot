package com.song.export.config.interceptor;

import com.song.export.annotation.SongCache;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class SongCacheInterceptor extends HandlerInterceptorAdapter {
    /*
    // 在执行目标方法之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandler");
        System.out.println(handler.getClass());
        return true;
    }

    // 执行目标方法之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler");
    }

    // 在请求已经返回之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();
        // 获取出方法上的Access注解
        SongCache songCache = method.getAnnotation(SongCache.class);
        if (songCache == null) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }
        if (songCache.cache() == true) {//需要走缓存查询
            String key = songCache.key1();
            sendResponse(response,key);
        }
        // 拦截之后应该返回公共结果, 这里没做处理
        return false;
    }

    public void sendResponse(HttpServletResponse response,String content){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        try{
            out = response.getWriter();
            out.append(content);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
