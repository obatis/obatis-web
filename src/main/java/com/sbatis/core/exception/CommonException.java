package com.sbatis.core.exception;


/**
 * 自定义公共异常类
 * @author HuangLongPu
 */
public class CommonException extends RuntimeException {
	
    /**
     * 错误提示码
     */
    private String errorCode = null;

    public CommonException(String message) {
        super(message);
    }
    public CommonException(String errCode, String message) {
    	super(message);
    	this.errorCode = errCode;
    }
	public String getErrCode() {
		return errorCode;
	}
    
}
