package com.sbatis.core.exception;

/**
 * description: 未认证异常
 */
public class NotAuthException extends RuntimeException  {
	
    private static final long serialVersionUID = -2386016023795321548L;

    /**
     * 异常信息
     */
    private String message;

    public NotAuthException(String message) {
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
