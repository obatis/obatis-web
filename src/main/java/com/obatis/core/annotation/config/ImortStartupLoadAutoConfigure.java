package com.obatis.core.annotation.config;

import com.obatis.config.SystemConstant;
import com.obatis.core.exception.HandleException;
import com.obatis.tools.ValidateTool;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 项目启动加载类，进行注解的注册、特定方法处理等
 * @author HuangLongPu
 */
@Configuration
@ConditionalOnWebApplication
public class ImortStartupLoadAutoConfigure implements ImportBeanDefinitionRegistrar {

    @Resource
    private Environment environment;

    @Bean
    public int loadSystemEnv() {
        SystemConstant.setSystemEnv(environment);
        return 0;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        String startupClassName = annotationMetadata.getClassName();
        if(!startupClassName.contains(".")) {
            /**
             * 说明启动类在缺省目录下
             */
            throw new HandleException("项目启动类不允许在缺省目录下");
        }
        String startupPackageName = startupClassName.substring(0, startupClassName.lastIndexOf("."));
        SystemConstant.PROJECT_BASE_DIR = startupPackageName;
        if(ValidateTool.isEmpty(startupPackageName)) {
            throw new HandleException("获取项目启动类包路径失败");
        }
        if(!"com".equals(startupPackageName) && !SystemConstant.CORE_BASE_DIR.equals(startupPackageName)) {
            /**
             * 是否使用默认的filter，使用默认的filter意味着只扫描那些类上拥有Component、Service、Repository或Controller注解的类。
             */
            ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(true);
            Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(startupPackageName);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                /**
                 * beanName通常由对应的BeanNameGenerator来生成，比如Spring自带的AnnotationBeanNameGenerator、DefaultBeanNameGenerator等，也可以自己实现。
                 */
                String beanName = beanDefinition.getBeanClassName();
                beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
            }
        } else {
            throw new HandleException("项目启动类不允许在com或者com.obatis下");
        }

        /**
         * 加载 Controller url
         */
        new LoadAnnotationUrl().load(startupPackageName);
    }

}
