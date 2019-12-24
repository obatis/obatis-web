package com.obatis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger2", value = {"enable"}, havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        System.out.println(SystemConstant.getRunDevEnv());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build().enable(SystemConstant.getRunDevEnv());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger接口开发文档")
                .description("")
                .build();
    }
}

