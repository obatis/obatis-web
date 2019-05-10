package com.obatis.email.impl;

import com.obatis.email.SendMailService;
import com.obatis.email.exception.SendMailException;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Component
public class SendMailServiceImpl implements SendMailService {

    @Resource
    private Environment env;
    @Resource
    private TemplateEngine templateEngine;

    private static JavaMailSender mailSender;
    private static String fromEmail = null;

    @Override
    public void send(String toEmail, String title, String content) throws SendMailException {
        getJavaMailSender(env);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
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
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new SendMailException("error:" + e.getMessage());
        }
    }

    /**
     * 使用模版的形式发送邮件
     * @param toEmail
     * @param title
     * @param templatePath
     * @param params
     * @throws SendMailException
     */
    @Override
    public void sendTemplate(String toEmail, String title, String templatePath, Map<String, Object> params) throws SendMailException {
        Context context = new Context();
        context.setVariables(params);
        this.send(toEmail, title, templateEngine.process(templatePath, context));
    }

    private static JavaMailSender getJavaMailSender(Environment env) {
        if(mailSender == null) {
            return loadJavaMailSender(env);
        }
        return mailSender;
    }

    /**
     * JavaMailSender 采用自定义方式而非注入的方式，目的为保证兼容性，在不使用邮件功能（未配置）时框架也能正常使用
     * @param env
     * @return
     */
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
