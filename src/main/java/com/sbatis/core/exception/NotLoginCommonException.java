package com.sbatis.core.exception;


/**
 * 自定义公共未登录异常类
 * @author HuangLongPu
 */
public class NotLoginCommonException extends RuntimeException {

	/**
	 * 异常信息
	 */
	private String message;

	public NotLoginCommonException() {
		super("账户未登录");
	}
	public NotLoginCommonException(String message) {
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
