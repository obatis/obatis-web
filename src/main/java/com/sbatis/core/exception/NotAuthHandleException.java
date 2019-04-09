package com.sbatis.core.exception;

/**
 * 自定义未认证异常类
 * @author HuangLongPu
 */
public class NotAuthHandleException extends RuntimeException  {

    /**
     * 异常信息
     */
    private String message;

    public NotAuthHandleException(String message) {
        super(message);
    }

	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}
	
    
}
