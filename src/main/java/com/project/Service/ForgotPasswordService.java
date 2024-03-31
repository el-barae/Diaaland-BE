package com.project.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.project.Entity.PasswordResetToken;
import com.project.Entity.User;
import com.project.Repository.PasswordResetTokenRepository;
import com.project.Repository.UserRepository;

@Service
public class ForgotPasswordService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final JavaMailSender javaMailSender;
 
    private final UserRepository userRepository;
    
    public ForgotPasswordService(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository, JavaMailSender javaMailSender) {
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.javaMailSender = javaMailSender;
		this.userRepository = userRepository;
    	
    }

    public String sendResetPasswordEmail(String email) {
        // Find the user by email address
        Optional<User> userOptional = userRepository.findByEmail(email);
        String token;
        if (userOptional.isEmpty()) {
            return null; // User not found, handle accordingly
        }
        User user = userOptional.get();
        Optional<PasswordResetToken> passwordResetTokenOp =  passwordResetTokenRepository.findByUser(user);
        if (passwordResetTokenOp.isEmpty()) {
        	// Generate a unique token
            token = generateToken();

            // Create a password reset token entity and save it in the database
            PasswordResetToken passwordResetToken = new PasswordResetToken();
            passwordResetToken.setUser(user);
            passwordResetToken.setToken(token);
            // Set an expiry time (e.g., 1 hour from now)
            passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
            passwordResetTokenRepository.save(passwordResetToken);
        }
        else {
        	PasswordResetToken passwordResetToken = passwordResetTokenOp.get();
        	token = generateToken();
        	passwordResetToken.setToken(token);
        	passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
            passwordResetTokenRepository.save(passwordResetToken);
        	}

        // Build the forgot password URL with the token
        String resetPasswordUrl = "http://localhost:3000/login/reset/ChangePass/" + "?token=" + token;

        // Create the email content
        String emailContent = "Please click the following link to reset your password: " + resetPasswordUrl;

        // Send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getUsername());
        message.setSubject("Password Reset");
        message.setText(emailContent);

        javaMailSender.send(message);
        //emailService.sendEmail(user.getUsername(), "Password Reset", emailContent);

        return token;
    }

    private String generateToken() {
        // Implement token generation logic (e.g., using UUID)
        return UUID.randomUUID().toString();
    }
}

