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
       
13.RabbitMQ整合
http://www.ityouknow.com/springboot/2016/11/30/springboot(%E5%85%AB)-RabbitMQ%E8%AF%A6%E8%A7%A3.html

14.阿里云
39.104.169.209
root
ss!

14.整合thymeleaf模板
http://www.cnblogs.com/nfcm/p/7843935.html

11111.全局异常处理 (需要 引入页面模板)