package com.obatis.startup;

import com.obatis.tools.ValidateTool;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.Map;

public final class MappingBeanMethodHandle {

    protected MappingBeanMethodHandle() {}

    /**
     * 加载带有 @Controller 和 @RestController 的Controller类
     * @param applicationContext
     */
    protected final void load(ApplicationContext applicationContext) {
        this.loadController(applicationContext, Controller.class);
        this.loadController(applicationContext, RestController.class);
    }

    private void loadController(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(annotationType);
        for (String beanName : beans.keySet()) {
            Object value = applicationContext.getBean(beanName);
            if (value == null) {
                continue;
            }

            RequestMapping requestMapping = AnnotationUtils.findAnnotation(value.getClass(), RequestMapping.class);
            if (requestMapping == null) {
                continue;
            }

            loadAnnotationUrlPath(value.getClass(), requestMapping);
        }
    }

    private void loadAnnotationUrlPath(Class<?> cls, RequestMapping mapping) {

        String path = "";
        if (mapping != null) {
            String[] pathArr = mapping.value();
            if (pathArr.length > 0 && !ValidateTool.isEmpty(pathArr[0])) {
                if(pathArr[0].startsWith("/")) {
                    path += pathArr[0];
                } else {
                    path += "/" + pathArr[0];
                }
            }
        }

        MappingBeanHandleAutoConfigure.handle(cls, cls.getCanonicalName(), path);
    }

}
