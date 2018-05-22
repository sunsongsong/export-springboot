package com.song.export.config.interceptor;

import com.song.export.annotation.SongCache;
import com.song.export.cache.RedisService;
import net.sf.json.JSONObject;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class SongCacheInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisService redisService;

    // 执行目标方法之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler");
    }





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
            String key = songCache.key();
            String value = songCache.value();

            Object content = redisService.get(value);

            if(content == null){//如果缓存中没有，则直接走方法进行查询 --考虑在方法执行后,拿到返回值,加入缓存
                return true;
            }
            sendResponse(response,content);
        }
        // 拦截之后应该返回公共结果, 这里没做处理
        return false;
    }

    // 在请求已经返回之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }

    public void sendResponse(HttpServletResponse response,Object content){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        try{
            out = response.getWriter();
            out.append(JSONObject.fromObject(content)+"");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
