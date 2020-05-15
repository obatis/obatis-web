package com.obatis.core.exception;

/**
 * 自定义公共未登录异常类
 * @author HuangLongPu
 */
public class NotLoginHandleException extends RuntimeException {

	public static final String DEFAULT_NOT_LOGIN_MESSAGE = "用户未登录";

	/**
	 * 异常信息
	 */
	private String message;

	public NotLoginHandleException() {
		super(DEFAULT_NOT_LOGIN_MESSAGE);
	}
	public NotLoginHandleException(String message) {
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
