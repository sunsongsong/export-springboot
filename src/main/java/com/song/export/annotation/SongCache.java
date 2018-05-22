package com.song.export.annotation;

import java.lang.annotation.*;

/**
 * 自定义缓存注解
 *      用途：方法级别上的获取缓存
 *      采用Hash方式进行缓存处理
 * 模拟@Cacheable的实现
 *      1.生成String类型的缓存,key为键,value为值,同时设置过期时间
 *      2.同时生成ZSet,key为键,value的值为String的key,当加入新的value时,更新ZSet的过期时间,当String类型的key过期时,ZSet的value需要移除
 *
 *
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SongCache {
    String key(); //
    String value(); //
    long time() default 300L;
    boolean cache() default true;
}
