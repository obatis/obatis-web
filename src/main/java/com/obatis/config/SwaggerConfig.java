package com.obatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Swagger 接口配置
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .apis(RequestHandlerSelectors.any()).paths(or(regex("/.*"))).build()
                .enable(SystemConstant.getRunDevEnv())
                .apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo() {

        Contact contact = new Contact("", "", "");

        return new ApiInfoBuilder().title("系统标题")// 大标题
                .description("描述")// 详细描述
                .version("V1.0")// 版本
                .termsOfServiceUrl("主页").contact(contact)
                .license("")
                .licenseUrl("").build();
    }


}
