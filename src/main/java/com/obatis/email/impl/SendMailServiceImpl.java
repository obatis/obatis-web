package com.obatis.email.impl;

import com.obatis.email.SendMailService;
import com.obatis.email.exception.SendMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendMailServiceImpl implements SendMailService {

    private final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);

//    @Value("${spring.mail.username}")
    //使用@Value注入application.properties中指定的用户名
    private String fromEmail;
    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void send(String toEmail, String title, String content) throws SendMailException {
        logger.warn("发送邮件到 " + toEmail);
        logger.warn("标题为： " + title);
        logger.warn("内容为： " + content);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(title);
            /**
             * 设置为 true，表示发送 html格式
             */
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("发送HTML邮件成功");
        } catch (MessagingException e) {
            logger.error("发送HTML邮件失败：", e);
            e.printStackTrace();
            throw new SendMailException("error:" + e.getMessage());
        }
    }
}
