1.使用IDEA新建一个Spring Boot项目
    https://blog.csdn.net/bobozai86/article/details/78835989

2.Spring Boot整合Spring MVC
    https://blog.csdn.net/xinluke/article/details/53967722

3.Spring Boot整合Json返回值
    <dependency>
        <groupId>net.sf.json-lib</groupId>
        <artifactId>json-lib</artifactId>
        <version>2.4</version>
        <classifier>jdk15</classifier>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.2</version>
    </dependency>

4.整合多配置文件
    spring.profiles.active=test

5.整合数据库
    https://www.cnblogs.com/liangblog/p/5228548.html

6.整合mybatis
    https://www.cnblogs.com/liangblog/p/5228548.html

7.使用mybatis逆向生成工具
    https://blog.csdn.net/z947511564/article/details/68107342
    注意使用mysql-connector-java用高版本会生成Bols文本

    常见错误：
    org.apache.ibatis.binding.BindingException: Invalid bound statement (not found):

    mybatis.mapperLocations=classpath:mapper/*.xml  是否配置正确

8.整合阿里druid数据库连接池  便于查看sql情况
    https://blog.csdn.net/CoffeeAndIce/article/details/78707819
    https://www.cnblogs.com/shyroke/p/8045077.html

9.日志配置
    https://blog.csdn.net/sdlyjzh/article/details/79416607
    排除spring boot中的logback的依赖包
    添加log4j2的依赖包
    如果用到异步日志的配置,需要添加disruptor包

10.多数据源配置
    https://my.oschina.net/lengchuan/blog/876429

11.Redis的配置
    https://www.cnblogs.com/nfcm/p/7833032.html

12.spring缓存注解的使用
    https://www.cnblogs.com/fashflying/p/6908028.html
    底层采用zset存储
        https://blog.csdn.net/u011271894/article/details/77969070
        我的理解：先是一个zset存储所有的key,再是string存储key-value的值
        这样能支持清除所有注解为value的缓存，不用一个一个的按key清除了

13.阿里云39.104.169.209rootss!

14.整合swagger接口

14.RabbitMQ整合
    http://www.ityouknow.com/springboot/2016/11/30/springboot(%E5%85%AB)-RabbitMQ%E8%AF%A6%E8%A7%A3.html
    https://www.cnblogs.com/boshen-hzb/p/6841982.html
     常见错误：
        (reply-code=404, reply-text=NOT_FOUND - no queue 'helloQueue' in vhost '/', class-id=50, method-id=10)
        缺少 helloQueue队列了,需要手动添加
        https://blog.csdn.net/a286352250/article/details/53158667

15.mysql数据库安装
    https://jingyan.baidu.com/article/a378c9609eb652b3282830fd.html

16.整合thymeleaf模板
    http://www.ityouknow.com/springboot/2017/09/23/spring-boot-jpa-thymeleaf-curd.html

17.全局异常处理 (需要 引入页面模板)
    增加全局异常处理类：GlobalExceptionHandler

18.验证码
    getVerify

19.文件导出
    util包下

20.定时任务
    @Scheduled

21.邮件服务
    mail

22.mongodb

23.jenkins/maven/git安装

24.自定义注解实现AOP    Aspect 和 Interceptor
   (1)缓存注解
    思路：自定义切面,拿到注解类的数据,进行切面处理

   (2)数据源切换 需要借用ThreadLocal

   说明：Filter和Interceptor 、Aspect区别
        过滤器(Filter)：可以拿到原始的http请求，但是拿不到你请求的控制器和请求控制器中的方法的信息。
            (1):implements Filter
            (2):WebConfig 中配置

        拦截器(Interceptor)：可以拿到你请求的控制器和方法，却拿不到请求方法的参数。
            (1)implements HandlerInterceptor

        切片(Aspect):可以拿到方法的参数，但是却拿不到http请求和响应的对象
            (1)使用环绕通知,可以拿到对应的 前置通知、后置通知、异常通知、返回通知

    具体应用：
    Aop与缓存

    Aop与日志
        https://blog.csdn.net/czmchen/article/details/42392985
        记录日志 (前置通知 记录方法名和参数,后置通知 记录返回结果)

    Aop与缓存
        参考AspectCache类





25.freemaker模板导出

26.泛型的使用

27.redis分布式锁实现秒杀系统
    https://mp.weixin.qq.com/s?__biz=MzA3ODQ0Mzg2OA==&mid=2649047660&idx=1&sn=95dbef28a16505971d2439b867cb8dfd&chksm=8753465fb024cf492aa8967acabaa854d22b9abca5cb060b9167de8f01bc8fc60102da4d68a4&scene=21#wechat_redirect

28.java 并发包中lock的使用







