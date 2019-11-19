package com.obatis.config.response.result.callback;

public class ExceptionRestParam {

    /**
     * 处理类型
     */
    private HandleTypeEnum handleType;
    /**
     * 异常信息
     */
    private Exception exception;

    public HandleTypeEnum getHandleType() {
        return handleType;
    }

    public void setHandleType(HandleTypeEnum handleType) {
        this.handleType = handleType;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
