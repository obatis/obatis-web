package com.sbatis.core.exception;


/**
 * 自定义公共异常类
 * @author HuangLongPu
 */
public class HandleException extends RuntimeException {
	
    /**
     * 错误提示码
     */
    private String errorCode = null;

    public HandleException(String message) {
        super(message);
    }
    public HandleException(String errCode, String message) {
    	super(message);
    	this.errorCode = errCode;
    }
	public String getErrCode() {
		return errorCode;
	}
    
}
