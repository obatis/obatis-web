package com.obatis.config.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

@Configuration
public class DynamicEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if(EnvironmentConstant.ENVIRONMENT_LOAD_FLAG) {
            return;
        }
        EnvironmentConstant.ENVIRONMENT_LOAD_FLAG = true;

        boolean loadPropertiesFlag = false;
        Properties properties = new Properties();
        if(environment.getProperty(EnvironmentConstant.SpringEn.SPRING_NOT_FOUND.getKey()) != null) {
            properties.setProperty(EnvironmentConstant.SpringEn.SPRING_NOT_FOUND.getKey(), EnvironmentConstant.SpringEn.SPRING_NOT_FOUND.getValue());
            loadPropertiesFlag = true;
        }
        if(environment.getProperty(EnvironmentConstant.SpringEn.RESOURCES_ADD_MAPPING.getKey()) != null) {
            properties.setProperty(EnvironmentConstant.SpringEn.RESOURCES_ADD_MAPPING.getKey(), EnvironmentConstant.SpringEn.RESOURCES_ADD_MAPPING.getValue());
            if(!loadPropertiesFlag) {
                loadPropertiesFlag = true;
            }
        }
        if(environment.getProperty(EnvironmentConstant.SpringEn.MYBATIS_MAP_UNDERSCORE.getKey()) != null) {
            properties.setProperty(EnvironmentConstant.SpringEn.MYBATIS_MAP_UNDERSCORE.getKey(), EnvironmentConstant.SpringEn.MYBATIS_MAP_UNDERSCORE.getValue());
            if(!loadPropertiesFlag) {
                loadPropertiesFlag = true;
            }
        }

        if(loadPropertiesFlag) {
            PropertiesPropertySource propertySource = new PropertiesPropertySource("dynamic", properties);
            environment.getPropertySources().addLast(propertySource);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}
