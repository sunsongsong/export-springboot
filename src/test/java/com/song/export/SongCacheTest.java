package com.song.export;

import com.song.export.annotation.SongCache;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SongCacheTest {

    @SongCache(key = "key",value = "value",time = 60L)
    public static String test(){
        return "test";
    }

    @SongCache(key = "key",value = "value")
    public static String test1(){
        return "test1";
    }

    public static void myTest(){
        System.out.println(test());
    }

    public static void myTest1(){
        System.out.println(test1());
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        cacheAOP();
    }

    public static Object cacheAOP() throws IllegalAccessException, InstantiationException, InvocationTargetException{
        Object object = null;
        Class clazz = SongCacheTest.class;
        Method[] ms = clazz.getMethods();
        for (Method method : ms) {
            if (method.isAnnotationPresent(SongCache.class)) {
                SongCache songCache = method.getAnnotation(SongCache.class);
                String key = songCache.key();
                String value = songCache.value();
                long time = songCache.time();
                System.out.println("key="+key+",value="+value+",time="+time);
                if(CanReturnCache(time)){
                    object = time;
                }else{
                    method.invoke(clazz.newInstance(), null);
                }
            }
        }
        return object;
    }

    /**
     * 是否从缓存直接返回 返回值
     * @param time
     * @return
     */
    public static boolean CanReturnCache(long time){
        if(time != 300L){
            return true;
        }
        return false;
    }
}
