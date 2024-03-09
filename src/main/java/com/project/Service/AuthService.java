package com.project.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.Entity.Candidates;
import com.project.Entity.Role;
import com.project.Repository.CandidateRepository;
import com.project.Repository.UserRepository;
import com.project.model.AuthResponseDto;
import com.project.model.LoginRequestDto;
import com.project.model.RegisterCandidateRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final CandidateRepository candidateRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  public  AuthService (JwtService jwtService, CandidateRepository candidateRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
      this.jwtService = jwtService;
      this.candidateRepository = candidateRepository;
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
      this.authenticationManager = authenticationManager;
  }


  public AuthResponseDto login(LoginRequestDto request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    var jwt = jwtService.generateToken(user);
    return AuthResponseDto.builder()
      .token(jwt)
      .build();
  }
  public AuthResponseDto registerCandidate(RegisterCandidateRequestDto request) {
    var candidate = Candidates.builder()
      .firstName(request.getFirstName())
      .lastName(request.getLastName())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .phoneNumber(request.getPhoneNumber())
      .description(request.getDescription())
      .adress(request.getAdress())
      .city(request.getCity())
      .country(request.getCountry())
      .role(Role.CANDIDAT)
      .build();
    candidateRepository.save(candidate);
    var jwt = jwtService.generateToken(candidate);
    return AuthResponseDto.builder()
      .token(jwt)
      .build();
  }
  public void logout(String token) {
      jwtService.revokeToken(token);
	    SecurityContextHolder.clearContext();  
  }
}
