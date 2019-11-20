# obatis-web
obatis体系web框架服务，基于springcloud的web项目微服务框架，不包含数据库编程。项目地址：https://github.com/obatis/obatis-web

maven jar包引入在maven中央仓库搜索obatis，引用相应的版本，或者打开链接 https://mvnrepository.com/artifact/com.obatis/obatis-web 即可。

在项目启动类上 加入 @StartupLoadAutoConfigure 注解，即引用 obatis 框架进行使用，参考如下代码： 

@StartupLoadAutoConfigure
@EnableEurekaClient
@EnableFeignClients
public class ApplicationStartup {

    public static void main(String[] args) {
       SpringApplication.run(ApplicationStartup.class, args);
    }

}