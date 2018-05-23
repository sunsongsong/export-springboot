package com.song.export.annotation;

import java.lang.annotation.*;

/**
 * 自定义缓存注解
 *      用途：方法级别上的获取缓存
 *      采用Hash方式进行缓存处理
 * 模拟@Cacheable的实现  --未实现
 *      1.生成String类型的缓存,key为键,value为值,同时设置过期时间
 *      2.同时生成ZSet,key为键,value的值为String的key,当加入新的value时,更新ZSet的过期时间,当String类型的key过期时,ZSet的value需要移除
 *
 * 由于以上为实现
 * 换种方式:使用Redis的String的数据结构(key-value)进行存储
 *      其中：key是方法名 + 注解的key
 *           value是缓存的值
 *
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AspectCacheAnnotation {
    /**
     * 使用Redis的String的数据结构(key-value)进行存储
     *
     */
    long time() default 300L;
}
