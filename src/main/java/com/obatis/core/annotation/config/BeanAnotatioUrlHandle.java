package com.obatis.core.annotation.config;

import com.obatis.core.annotation.request.NotLogin;
import com.obatis.core.exception.HandleException;
import com.obatis.validate.ValidateTool;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanAnotatioUrlHandle {

    private static final Map<String, String> URL_MAP = new HashMap<>();

    protected BeanAnotatioUrlHandle() {}

    protected static final void handle(Class<?> beanCls, String canonicalName, String controllerPath) {
        Method[] methodArr = beanCls.getDeclaredMethods();
        for (Method method : methodArr) {
            String[] pathArr = null;

            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
            if(mapping == null) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                if(postMapping == null) {
                    GetMapping getMapping = method.getAnnotation(GetMapping.class);
                    if(getMapping != null) {
                        pathArr = getMapping.value();
                    }
                } else {
                    pathArr = postMapping.value();
                }
            } else {
                pathArr = mapping.value();
            }

            if(pathArr == null) {
                continue;
            }

            if(pathArr.length != 1) {
                throw new HandleException("error:Annotation error: " + canonicalName + " method " + method.getName() + " RequestMapping value not only one!!!");
            }

            String path = null;
            if(!ValidateTool.isEmpty(pathArr[0])) {
                if(pathArr[0].startsWith("/")) {
                    path = pathArr[0];
                } else {
                    path = "/" + pathArr[0];
                }
            }

            if(path == null) {
                throw new HandleException("error:Annotation error: " + canonicalName + " method " + method.getName() + " url annotation is null!!!");
            }


            String urlName = null;
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if(apiOperation != null) {
                urlName = apiOperation.value();
            }

            /**
             * 缓存URL地址
             */
            URL_MAP.put(controllerPath + path, urlName);

            NotLogin notAuth = method.getAnnotation(NotLogin.class);
            if(notAuth != null) {
                NotLoginAnnotationUrl.putNotLoginAnnotationUrl(controllerPath + path, urlName);
            }
        }
    }
}
