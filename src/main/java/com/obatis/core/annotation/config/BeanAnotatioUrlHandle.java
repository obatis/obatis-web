package com.obatis.core.annotation.config;

import com.obatis.core.exception.HandleException;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

public class BeanAnotatioUrlHandle {

    protected UrlAnotatioHandle() {}

    protected static final void handle(Class<?> beanCls, String canonicalName, String controllerPath, List<UrlInfoInput> beanList, List<String> notAuthList) {
        Method[] methodArr = beanCls.getDeclaredMethods();
        for (Method method : methodArr) {
            String[] pathArr = null;
            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
//            if(mapping == null) {
//                PostMapping postMapping = method.getAnnotation(PostMapping.class);
//                if(postMapping == null) {
//                    GetMapping getMapping = method.getAnnotation(GetMapping.class);
//                    if(getMapping != null) {
//                        pathArr = getMapping.value();
//                    }
//                } else {
//                    pathArr = postMapping.value();
//                }
//            } else {
                pathArr = mapping.value();
//            }

            if(pathArr == null) {
                continue;
            }

            if(pathArr.length != 1) {
                throw new HandleException("Annotation error: " + canonicalName + " method " + method.getName() + " RequestMapping value not only one!!!");
            }

            String path = pathArr[0];
            if(!path.startsWith("/")) {
                path = "/" + path;
            }

            UrlInfoInput urlInfoInput = new UrlInfoInput();
            urlInfoInput.setFunctionUrl(controllerPath + path);
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if(apiOperation != null) {
                urlInfoInput.setFunctionName(apiOperation.value());
            }
            beanList.add(urlInfoInput);

            NotAuth notAuth = method.getAnnotation(NotAuth.class);
            if(notAuth != null) {
                notAuthList.add(urlInfoInput.getFunctionUrl());
            }
        }
    }
}
