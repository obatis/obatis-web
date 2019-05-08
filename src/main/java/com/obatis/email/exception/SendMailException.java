package com.obatis.email.exception;

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
