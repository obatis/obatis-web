package com.obatis.config.url;

public class UrlBeanInfo {

    /**
     * url地址名称
     */
    private String resourceName;
    /**
     * url地址
     */
    private String url;
    /**
     * 是否需要登录 0表示需要登录，1表示不需要登录
     */
    private int isLoginEnable;
    /**
     * 请求方式，get和post
     */
    private String requestType;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsLoginEnable() {
        return isLoginEnable;
    }

    public void setIsLoginEnable(int isLoginEnable) {
        this.isLoginEnable = isLoginEnable;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
