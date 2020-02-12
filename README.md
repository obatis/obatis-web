# obatis-web
obatis体系web框架服务，基于springcloud的web项目微服务框架，不包含数据库编程。项目地址：https://github.com/obatis/obatis-web



maven jar包引入在maven中央仓库搜索obatis，引用相应的版本，或者打开链接 https://mvnrepository.com/artifact/com.obatis/obatis-web 找到相应的版本，引入项目即可。

引入obatis体系，需在项目启动类上 加入 @StartupLoadAutoConfigure 注解，即引用 obatis 框架进行使用，参考如下代码：   

```java
@StartupLoadAutoConfigure
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableScheduling
public class ApplicationStartup {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStartup.class, args);
    }

}
```

注意：如果作为单体应用开发，springboot项目配置文件中加入以下配置表示不去注册中心进行注册，否则会报错，提示找不到注册中心地址  

```xml
eureka.client.fetch-registry = false  
eureka.client.register-with-eureka = false 
```

