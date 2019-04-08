package com.sbatis.core.exception;


/**
 * description: 业务异常类
 */
public class BusinessException extends RuntimeException {
	
    /**
     * 错误提示码
     */
    private String errorCode = null;

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String errCode, String message) {
    	super(message);
    	this.errorCode = errCode;
    }
	public String getErrCode() {
		return errorCode;
	}
    
}
