package com.obatis.email.exception;

/**
 * 自定义邮件发送异常类，邮件发送失败时抛出此异常
 */
public class SendMailException extends Exception {

    public SendMailException(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
