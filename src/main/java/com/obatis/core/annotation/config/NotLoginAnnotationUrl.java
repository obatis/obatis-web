package com.obatis.core.annotation.config;

import com.obatis.tools.ValidateTool;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存添加注解 @NotLogin 的Controller Url路径
 * @author HuangLongPu
 */
public final class NotLoginAnnotationUrl {

    private static final Map<String, String> NOT_LOGIN_URL = new HashMap<>();

    protected NotLoginAnnotationUrl() {}

    /**
     * 放置添加注解 @NotLogin 的Controller Url路径
     * @param url
     */
    protected static final void putNotLoginAnnotationUrl(String url, String urlName) {
        if(ValidateTool.isEmpty(url)) {
            return;
        }
        NOT_LOGIN_URL.put(url, urlName);
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
        return NOT_LOGIN_URL.containsKey(url);
    }
}
