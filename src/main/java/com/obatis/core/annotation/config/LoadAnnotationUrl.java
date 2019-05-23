package com.obatis.core.annotation.config;

import com.obatis.validate.ValidateTool;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

public final class LoadAnnotationUrl {

    protected LoadAnnotationUrl() {}

    protected final void load(String baseDir) {
        Reflections reflections = new Reflections(baseDir);
        /**
         * 获取到所有的 Controller 注解
         */
        Set<Class<?>> controllerList = reflections.getTypesAnnotatedWith(Controller.class);

        System.out.println(controllerList.size());

        this.handle(controllerList);
    }

    private final void handle(Set<Class<?>> controllerList) {
        for (Class<?> cla : controllerList) {
            String path = "";
            // 表示注解为 Controller
            Controller controller = cla.getAnnotation(Controller.class);
            path = controller.value();

            RequestMapping mapping = cla.getAnnotation(RequestMapping.class);
            if (mapping != null) {
                String[] pathArr = mapping.value();
                if (pathArr.length == 1) {
                    path += "/" + pathArr[0];
                }
            }

            if (!ValidateTool.isEmpty(path) && !path.startsWith("/")) {
                path = "/" + path;
            }

            // 加载方法上的 url path 注解
//            UrlAnotatioHandle.handle(cla, cla.getCanonicalName(), path, urlList, notAuthList);
        }
    }
}
