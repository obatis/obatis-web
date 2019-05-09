package com.obatis.email.impl;

import com.obatis.email.SendMailService;
import com.obatis.email.exception.SendMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendMailServiceImpl implements SendMailService {

    private final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);

    @Resource
    private Environment env;

    private static JavaMailSender mailSender;
    private static String fromEmail = null;

    @Override
    public void send(String toEmail, String title, String content) throws SendMailException {
        logger.warn("发送邮件到 " + toEmail);
        logger.warn("标题为： " + title);
        logger.warn("内容为： " + content);
        getJavaMailSender(env);
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

    private static JavaMailSender getJavaMailSender(Environment env) {
        if(mailSender == null) {
            return loadJavaMailSender(env);
        }
        return mailSender;
    }

    private static synchronized JavaMailSender loadJavaMailSender(Environment env) {
        if(mailSender == null) {
            mailSender = new JavaMailSenderImpl();
            ((JavaMailSenderImpl) mailSender).setHost(env.getProperty("spring.mail.host"));
            ((JavaMailSenderImpl) mailSender).setUsername(env.getProperty("spring.mail.username"));
            ((JavaMailSenderImpl) mailSender).setPassword(env.getProperty("spring.mail.password"));
            ((JavaMailSenderImpl) mailSender).setDefaultEncoding(env.getProperty("spring.mail.default-encoding", "UTF-8"));
            fromEmail = env.getProperty("mail.fromMail.addr");
        }
        return mailSender;
    }
}
