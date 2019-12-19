package com.obatis.email.impl;

import com.obatis.config.SystemConstant;
import com.obatis.constant.NormalCommonConstant;
import com.obatis.core.exception.HandleException;
import com.obatis.email.SendMailService;
import com.obatis.email.exception.SendMailException;
import com.obatis.validate.ValidateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

/**
 * 发送邮件构造方法构造类
 * 加入到Spring bean 对象，直接使用引入注解标签即可
 */
@Component
public class SendMailServiceImpl implements SendMailService {

    private static final Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);

    @Resource
    private TemplateEngine templateEngine;

    private static JavaMailSender mailSender;
    private static String fromEmail = null;
    private static String fromEmailPerson = null;
    private static final Integer defaultPort= 465;
    private static String encoding;

    @Override
    public void send(String toEmail, String title, String content) throws SendMailException {
        getJavaMailSender(SystemConstant.ENV);
        if(mailSender == null) {
            log.warn("邮件信息配置不正确，检查后请重启服务器");
            throw new SendMailException("邮件信息配置不正确，检查后请重启服务器");
        }
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            System.out.println("encoding >>> " + encoding);
            helper = new MimeMessageHelper(message, true, encoding);
            if(!ValidateTool.isEmpty(fromEmailPerson)) {
                helper.setFrom(fromEmail, fromEmailPerson);
            } else {
                helper.setFrom(fromEmail);
            }

            helper.setTo(toEmail);
            helper.setSubject(title);
            /**
             * 设置为 true，表示发送 html格式
             */
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
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
            fromEmail = env.getProperty("mail.username");
            String configPwd = env.getProperty("mail.password");
            if(ValidateTool.isEmpty(fromEmail) || ValidateTool.isEmpty(configPwd)) {
                throw new HandleException("邮件信息配置不正确");
            }

            String configHost = env.getProperty("mail.host", "smtp." + fromEmail.substring(fromEmail.lastIndexOf("@") + 1));
            ((JavaMailSenderImpl) mailSender).setHost(configHost);
            encoding = env.getProperty("mail.encoding", NormalCommonConstant.CHARSET_UTF8);
            ((JavaMailSenderImpl) mailSender).setDefaultEncoding(encoding);
            fromEmailPerson = env.getProperty("mail.fromMail.person");
            if(!ValidateTool.isEmpty(fromEmailPerson)) {
                try {
                    fromEmailPerson = new String(fromEmail.getBytes(), encoding);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            ((JavaMailSenderImpl) mailSender).setUsername(fromEmail);
            ((JavaMailSenderImpl) mailSender).setPassword(configPwd);
            Properties javaMailProperties = new Properties();
            javaMailProperties.setProperty("mail.transport.protocol", "smtp");// 设置传输协议
            javaMailProperties.setProperty("mail.smtp.ssl.enable", "true");
            javaMailProperties.setProperty("mail.smtp.ssl.trust", configHost);
            javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            String smtpPort = env.getProperty("mail.port", defaultPort.toString());
            javaMailProperties.setProperty("mail.smtp.socketFactory.port", smtpPort);
            javaMailProperties.setProperty("mail.smtp.port", smtpPort);
            javaMailProperties.setProperty("mail.smtp.auth", "true");
            javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
            javaMailProperties.setProperty("mail.smtp.starttls.required", "true");
            ((JavaMailSenderImpl) mailSender).setJavaMailProperties(javaMailProperties);
        }
        return mailSender;
    }

}
