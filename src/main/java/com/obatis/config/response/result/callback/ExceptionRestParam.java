package com.obatis.config.response.result.callback;

public class ExceptionRestParam {

    /**
     * 处理类型
     */
    private ExceptionHandleTypeEnum handleType;
    /**
     * 异常信息
     */
    private Exception exception;

    public ExceptionHandleTypeEnum getHandleType() {
        return handleType;
    }

    public void setHandleType(ExceptionHandleTypeEnum handleType) {
        this.handleType = handleType;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
