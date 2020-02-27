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
        boolean loadPropertiesFlag = false;
        Properties properties = new Properties();
        if(environment.getProperty(EnvironmentConfigEnum.MYBATIS_MAP_UNDERSCORE.getKey()) == null) {
            properties.setProperty(EnvironmentConfigEnum.MYBATIS_MAP_UNDERSCORE.getKey(), EnvironmentConfigEnum.MYBATIS_MAP_UNDERSCORE.getValue());
            loadPropertiesFlag = true;
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
