package com.obatis.email;

import com.obatis.email.exception.SendMailException;

public interface SendMailService {

    void send(String toEmail, String title, String content) throws SendMailException;
}
