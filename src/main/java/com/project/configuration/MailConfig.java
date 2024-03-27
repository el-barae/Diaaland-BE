package com.project.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
	@Value("${spring.mail.host}")
	  private String mailHost;
	  @Value("${spring.mail.port}")
	  private String mailPort;
	  @Value("${spring.mail.username}")
	  private String mailUsername;
	  @Value("${spring.mail.password}")
	  private String mailPassword;

    @Bean
     JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Set your mail server host
        mailSender.setPort(587); // Set your mail server port
        mailSender.setUsername("esender699@gmail.com"); // Set your email address
        mailSender.setPassword("sender-23"); // Set your email password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
