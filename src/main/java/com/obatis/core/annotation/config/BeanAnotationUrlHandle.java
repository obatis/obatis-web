package com.obatis.core.annotation.config;

import com.obatis.config.url.UrlBeanInfo;
import com.obatis.constant.http.HttpConstant;
import com.obatis.core.annotation.request.NotLogin;
import com.obatis.core.exception.HandleException;
import com.obatis.tools.ValidateTool;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanAnotationUrlHandle {

    /**
     * url 存储的 map 类，key 为controller注解的URL地址，value 为注册URL的方法说明(借助于 swagger实现)
     */
    private static final List<UrlBeanInfo> URL_INFO_LIST = new ArrayList<>();

    protected BeanAnotationUrlHandle() {}

    protected static final void handle(Class<?> beanCls, String canonicalName, String controllerPath) {
        Method[] methodArr = beanCls.getDeclaredMethods();
        for (Method method : methodArr) {
            String[] pathArr = null;
            String requestType = null;

            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
            if(mapping == null) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                if(postMapping == null) {
                    GetMapping getMapping = method.getAnnotation(GetMapping.class);
                    if(getMapping != null) {
                        pathArr = getMapping.value();
                        requestType = HttpConstant.METHOD_GET;
                    }
                } else {
                    pathArr = postMapping.value();
                    requestType = HttpConstant.METHOD_POST;
                }
            } else {
                pathArr = mapping.value();
                requestType = mapping.method()[0].name();
            }

            if(pathArr == null) {
                continue;
            }

            if(pathArr.length != 1) {
                throw new HandleException("error: " + canonicalName + " method " + method.getName() + " RequestMapping value not only one");
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
                throw new HandleException("error: " + canonicalName + " method " + method.getName() + " url annotation is null");
            }


            String urlName = null;
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if(apiOperation != null) {
                urlName = apiOperation.value();
            }

            /**
             * 缓存URL地址
             */
//            URL_MAP.put(, urlName);

            NotLogin notAuth = method.getAnnotation(NotLogin.class);
            int isLoginEnable = 0;
            if(notAuth != null) {
                NotLoginAnnotationUrl.putNotLoginAnnotationUrl(controllerPath + path, urlName);
                isLoginEnable = 1;
            }

            UrlBeanInfo beanInfo = new UrlBeanInfo();
            beanInfo.setResourceName(urlName);
            beanInfo.setUrl(controllerPath + path);
            beanInfo.setRequestType(requestType);
            beanInfo.setIsLoginEnable(isLoginEnable);
            URL_INFO_LIST.add(beanInfo);
        }
    }

    /**
     * 获取所以url地址信息列表
     * @return
     */
    public final static List<UrlBeanInfo> getUrlInfo() {
        return URL_INFO_LIST;
    }

}
