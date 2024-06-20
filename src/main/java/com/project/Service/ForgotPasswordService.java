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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String sendResetPasswordEmail(String email) {
        // Fetch user by email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return null; // User not found, handle accordingly
        }

        User user = userOptional.get();
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user).orElse(new PasswordResetToken());

        // Generate a new token and set the expiry time
        String token = generateToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        // Save the token
        passwordResetTokenRepository.save(passwordResetToken);

        // Build the reset password URL
        String resetPasswordUrl = "http://localhost:3000/login/reset/ChangePass/?token=" + token;

        // Create and send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getUsername());
        message.setSubject("Password Reset");
        message.setText("Please click the following link to reset your password: " + resetPasswordUrl);

        javaMailSender.send(message);

        return token;
    }

    private String generateToken() {
        // Implement token generation logic (e.g., using UUID)
        return UUID.randomUUID().toString();
    }
}

