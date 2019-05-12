package com.obatis.config.request;

public class RequestInfo {
    private String requestIp;
    private String requestSystem;
    private String requestBrowser;
    private String deviceType;

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
}
