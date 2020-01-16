package com.obatis.core.annotation.config;

import com.obatis.core.annotation.request.ConfigFeignClient;
import com.obatis.tools.ValidateTool;
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
         * 处理所有注解@Controller 的URL路径
         */
        this.handleController(reflections.getTypesAnnotatedWith(Controller.class));
        /**
         * 处理所有注解@RestController 的URL路径
         */
        this.handleRestController(reflections.getTypesAnnotatedWith(RestController.class));
        /**
         * 获取到所有注解@ConfigFeignClient 的微服务接口
         */
        this.handleConfigFeignClient(reflections.getTypesAnnotatedWith(ConfigFeignClient.class));
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

    private void handleConfigFeignClient(Set<Class<?>> configFeignClientList) {
        /**
         * 该方法尚未完全实现，暂时不提供使用
         */
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
