package com.obatis.config.request;

public class RequestInfo {

    /**
     * 请求设备IP地址
     */
    private String requestIp;
    /**
     * 请求设备操作系统
     */
    private String requestSystem;
    /**
     * 请求设备浏览器
     */
    private String requestBrowser;
    /**
     * 请求设备类型
     */
    private String deviceType;
    /**
     * 端口信息
     */
    private int remotePort;

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestSystem() {
        return requestSystem;
    }

    public void setRequestSystem(String requestSystem) {
        this.requestSystem = requestSystem;
    }

    public String getRequestBrowser() {
        return requestBrowser;
    }

    public void setRequestBrowser(String requestBrowser) {
        this.requestBrowser = requestBrowser;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }
}
