package com.obatis.core.annotation.config;

import com.obatis.config.SystemConstant;
import com.obatis.validate.ValidateTool;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

public class ImortStartupLoadAutoConfigure implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        String startupClassName = annotationMetadata.getClassName();
        String startupPackageName = startupClassName.substring(0, startupClassName.lastIndexOf("."));
        SystemConstant.PROJECT_BASE_DIR = startupPackageName;
        if(ValidateTool.isEmpty(startupPackageName)) {
            return;
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
        }

        /**
         * 加载 Controller url
         */
        new LoadAnnotationUrl().load(startupPackageName);
    }

}
