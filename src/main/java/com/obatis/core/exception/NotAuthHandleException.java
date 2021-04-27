package com.obatis.core.exception;

/**
 * 自定义未认证异常类
 * @author HuangLongPu
 */
public class NotAuthHandleException extends RuntimeException  {

	public static final String DEFAUTL_NOT_AUTH_MESSAGE = "请求未授权，没有操作权限";

    public NotAuthHandleException() {
    	this(DEFAUTL_NOT_AUTH_MESSAGE);
	}
    public NotAuthHandleException(String message) {
        super(message);
    }
    
}
