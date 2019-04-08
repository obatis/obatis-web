package com.sbatis.core.exception;


/**
 * description: 未登录异常
 */
public class NotLoginException extends RuntimeException {
	private static final long serialVersionUID = 4344663236819740487L;
	/**
	 * 异常信息
	 */
	private String message;

	public NotLoginException() {
		super("未登录");
	}
	public NotLoginException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
