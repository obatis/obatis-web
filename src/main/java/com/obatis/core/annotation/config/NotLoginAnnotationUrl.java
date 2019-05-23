package com.obatis.core.annotation.config;

import com.obatis.validate.ValidateTool;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存添加注解 @NotLogin 的Controller Url路径
 * @author HuangLongPu
 */
public final class NotLoginAnnotationUrl {

    private static final Map<String, Boolean> NOT_LOGIN_URL = new HashMap<>();

    private NotLoginAnnotationUrl() {}

    /**
     * 放置添加注解 @NotLogin 的Controller Url路径
     * @param url
     */
    protected final static void putNotLoginAnnotationUrl(String url) {
        if(ValidateTool.isEmpty(url)) {
            return;
        }
        NOT_LOGIN_URL.put(url, true);
    }

    /**
     * 通过传入的URL路径，判断是否为不需要登录的接口，如果返回为true，则表示无需登录验证
     * @param url
     * @return
     */
    public final static boolean validateNotLogin(String url) {
        if(ValidateTool.isEmpty(url)) {
            return false;
        }
        Boolean flag = NOT_LOGIN_URL.get(url);
        if(flag == null) {
            return false;
        }
        return flag;
    }
}
