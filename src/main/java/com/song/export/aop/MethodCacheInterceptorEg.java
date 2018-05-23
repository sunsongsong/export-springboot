package com.song.export.aop;

public class MethodCacheInterceptorEg {

}
/*
*//**
 *  作者：wang vincent
    链接：https://www.zhihu.com/question/39510340/answer/115948052
    来源：知乎
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *//*
@Aspect
@Component
public class MethodCacheInterceptorEg {


    private static final Logger logger = LoggerFactory.getLogger(MethodCacheInterceptorEg.class);
    private static final String CACHE_NAME = "Your unique cache name";


    @Autowired
    private CentralizeCacheService centralizeCacheService;

    *//**
     * 搭配 AspectJ 指示器“@annotation()”可以使本切面成为某个注解的代理实现
     *//*
    @Around("@annotation(your.package.MethodCache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String cacheKey = getCacheKey(joinPoint);
        Serializable serializable = centralizeCacheService.get(CACHE_NAME, cacheKey);
        if (serializable != null) {
            logger.info("cache hit，key [{}]", cacheKey);
            return serializable;
        } else {
            logger.info("cache miss，key [{}]", cacheKey);
            Object result = joinPoint.proceed(joinPoint.getArgs());
            if (result == null) {
                logger.error("fail to get data from source，key [{}]", cacheKey);
            } else {
                MethodCache methodCache = getAnnotation(joinPoint, MethodCache.class);
                centralizeCacheService.put(CACHE_NAME, methodCache.expire(), cacheKey, (Serializable) result);
            }
            return result;
        }
    }

    *//**
     * 根据类名、方法名和参数值获取唯一的缓存键
     * @return 格式为 "包名.类名.方法名.参数类型.参数值"，类似 "your.package.SomeService.getById(int).123"
     *//*
    private String getCacheKey(ProceedingJoinPoint joinPoint) {
        return String.format("%s.%s",
                joinPoint.getSignature().toString().split("\\s")[1], StringUtils.join(joinPoint.getArgs(), ","));
    }

    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint jp, Class<T> clazz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(clazz);
    }
}*/
