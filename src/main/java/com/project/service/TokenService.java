package com.project.service;

public interface TokenService {
    public void revokeToken(String token);
    boolean isTokenRevoked(String token);
    // D'autres méthodes liées à la gestion des jetons
}
