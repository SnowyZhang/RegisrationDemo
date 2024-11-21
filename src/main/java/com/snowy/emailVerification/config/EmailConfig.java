package com.snowy.emailVerification.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");  // SMTP 服务器地址
        mailSender.setPort(1025);  // SMTP 端口

        // 设置邮件认证信息
        mailSender.setUsername("hello");
        mailSender.setPassword("hello");

        // 配置邮件属性
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "false");  // 启用SSL加密

        return mailSender;
    }
}
