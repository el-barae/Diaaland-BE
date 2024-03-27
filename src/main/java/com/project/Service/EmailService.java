package com.project.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.project.Entity.User;

@Service
public interface EmailService  {
	
	void sendEmail(String to, String subject, String message);
	/*@Autowired
	JavaMailSender javaMailSender;
	
	public default String sendEmail(User user) {
		try {
			String resetLink = generateResetToken(user);

			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("");// input the senders email ID
			msg.setTo(user.getUsername());

			msg.setSubject("Welcome To My Channel");
			msg.setText("Hello \n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
					+ "Regards \n" + "ABC");

			javaMailSender.send(msg);

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}*/
}