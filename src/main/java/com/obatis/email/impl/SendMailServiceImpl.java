package com.obatis.email.impl;

import com.obatis.config.SystemConstant;
import com.obatis.email.SendMailService;
import com.obatis.email.exception.SendMailException;
import com.obatis.validate.ValidateTool;
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
import java.util.Properties;

/**
 * 发送邮件构造方法构造类
 * 加入到Spring bean 对象，直接使用引入注解标签即可
 */
@Component
public class SendMailServiceImpl implements SendMailService {

    @Resource
    private TemplateEngine templateEngine;

    private static JavaMailSender mailSender;
    private static String fromEmail = null;
    private static final Integer defaultPort= 465;

    @Override
    public void send(String toEmail, String title, String content) throws SendMailException {
        getJavaMailSender(SystemConstant.ENV);
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
     * 使用 thymeleaf 模版的形式发送邮件
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

    /**
     * 构造 JavaMailSender 对象
     * @param env
     * @return
     */
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
            ((JavaMailSenderImpl) mailSender).setHost(env.getProperty("mail.host"));
            ((JavaMailSenderImpl) mailSender).setUsername(env.getProperty("mail.username"));
            ((JavaMailSenderImpl) mailSender).setPassword(env.getProperty("mail.password"));
            ((JavaMailSenderImpl) mailSender).setDefaultEncoding(env.getProperty("mail.default-encoding", "UTF-8"));
            Properties javaMailProperties = new Properties();
            javaMailProperties.setProperty("mail.smtp.ssl.enable", "true");
            javaMailProperties.setProperty("mail.smtp.ssl.trust", ((JavaMailSenderImpl) mailSender).getHost());
            javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            String smtpPort = ValidateTool.isNumber(env.getProperty("mail.port")) ? env.getProperty("mail.port") : defaultPort.toString();
            javaMailProperties.setProperty("mail.smtp.socketFactory.port", smtpPort);
            javaMailProperties.setProperty("mail.smtp.port", smtpPort);
            javaMailProperties.setProperty("mail.smtp.auth", "true");
            javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
            javaMailProperties.setProperty("mail.smtp.starttls.required", "true");
            ((JavaMailSenderImpl) mailSender).setJavaMailProperties(javaMailProperties);
            fromEmail = env.getProperty("mail.fromMail.addr");
        }
        return mailSender;
    }
}
