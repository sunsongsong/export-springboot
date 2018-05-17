package com.song.export.annotation;

import java.lang.annotation.*;

/**
 * 自定义缓存注解
 *      用途：方法级别上的获取缓存
 *      采用Hash方式进行缓存处理
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SongCache {
    String key1(); //
    String key2(); //
    long time() default 300L;
    boolean cache() default true;
}
