package com.obatis.core.annotation.config;

import com.obatis.validate.ValidateTool;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

public final class LoadAnnotationUrl {

    protected LoadAnnotationUrl() {}

    /**
     * 加载带有 @Controller 和 @RestController 的Controller类
     * @param baseDir
     */
    protected final void load(String baseDir) {

        /**
         * 扫码基础目录下的文件
         */
        Reflections reflections = new Reflections(baseDir);
        /**
         * 获取到所有的 Controller 注解
         */
        Set<Class<?>> controllerList = reflections.getTypesAnnotatedWith(Controller.class);
        /**
         * 获取到所有的 RestController 注解
         */
        Set<Class<?>> restControllerList = reflections.getTypesAnnotatedWith(RestController.class);
        /**
         * 处理 URL路径
         */
        this.handleController(controllerList);
        /**
         * 异常处理回调
         */
        this.handleRestController(restControllerList);
    }

    private final void handleController(Set<Class<?>> controllerList) {

        for (Class<?> cls : controllerList) {
            // 表示注解为 Controller
            Controller controller = cls.getAnnotation(Controller.class);
            getAnnotationUrlPath(cls, handlePath(controller.value()));
        }
    }

    private void handleRestController(Set<Class<?>> restControllerList) {
        for (Class<?> cls : restControllerList) {
            // 表示注解为 RestController
            RestController resController = cls.getAnnotation(RestController.class);
            getAnnotationUrlPath(cls, handlePath(resController.value()));
        }
    }

    /**
     * 处理注解在 Controller上的URL
     * @param annotationPath
     * @return
     */
    private String handlePath(String annotationPath) {
        String path = null;
        if(!ValidateTool.isEmpty(annotationPath)) {
            if(annotationPath.startsWith("/")) {
                path = annotationPath;
            } else {
                path = "/" + annotationPath;
            }
        }

        if(path == null) {
            path = "";
        }
        return path;
    }

    private void getAnnotationUrlPath(Class<?> cls, String path) {

        RequestMapping mapping = cls.getAnnotation(RequestMapping.class);
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

        BeanAnotatioUrlHandle.handle(cls, cls.getCanonicalName(), path);
    }

}
