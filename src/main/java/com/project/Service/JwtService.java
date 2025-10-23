package com.project.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import com.project.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService{
	
	private Set<String> revokedTokens = new HashSet<>();
	
  private static final String SECRET_KEY = "77397A244326462948404D635166546A576E5A7234753778214125442A472D4B";

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(User user) {
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("id", user.getId());
    extraClaims.put("role", user.getRole().name()); // .name() si c’est un enum

    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(user.getEmail()) // email = username
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 jour
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts
      .builder()
      .setClaims(extraClaims)
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenRevoked(token));
  }

  private boolean isTokenRevoked(String token) {
      return revokedTokens.contains(token);
  }

  public void revokeToken(String token) {
      revokedTokens.add(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
  private Claims extractAllClaims(String token) {
	    return Jwts
	      .parserBuilder()
	      .setSigningKey(getSignInKey())
	      .build()
	      .parseClaimsJws(token)
	      .getBody();
	  }
  
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}