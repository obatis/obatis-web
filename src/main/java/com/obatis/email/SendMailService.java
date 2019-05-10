package com.obatis.email;

import com.obatis.email.exception.SendMailException;

import java.util.Map;

public interface SendMailService {

    /**
     * 发送 html 格式的邮件
     * @param toEmail
     * @param title
     * @param content
     * @throws SendMailException
     */
    void send(String toEmail, String title, String content) throws SendMailException;

    /**
     * 发送 thymeleaf模板邮件
     * @param toEmail
     * @param title
     * @param templatePath
     * @param params
     * @throws SendMailException
     */
    void sendTemplate(String toEmail, String title, String templatePath, Map<String, Object> params) throws SendMailException;
}
