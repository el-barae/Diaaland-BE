package com.project.Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginAttemptService {

    private final Map<String, Integer> attempts = new HashMap<>();

    public void trackLoginAttempt(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
    }

    public int getLoginAttempts(String username) {
        return attempts.getOrDefault(username, 0);
    }

    public void resetLoginAttempts(String username) {
        attempts.remove(username);
    }
}
