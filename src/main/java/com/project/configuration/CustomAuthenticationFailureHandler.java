package com.project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.project.Service.LogoutService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    //@Autowired
  /*  private LogoutService loginAttemptService;

    private int maxAttempts = 3;
    
    private String username;
    
    public void setUsername(String u) {
    	this.username=u;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
    	if (exception instanceof LockedException) {
            // Le compte est verrouillé en raison de trop nombreuses tentatives d'authentification
            response.getWriter().write("Le compte est verrouillé en raison de trop nombreuses tentatives d'authentification.");
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
        if (username != null) {
            loginAttemptService.trackLoginAttempt(username);
            int attemptCount = loginAttemptService.getLoginAttempts(username);

            if (attemptCount >= maxAttempts) {
                // Handle the case when the maximum attempts are reached
                super.onAuthenticationFailure(request, response, exception);
            } else {
                // Handle the failure for attempts less than the maximum
                response.sendRedirect(request.getContextPath() + "/login?error");
            }
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }*/
}
