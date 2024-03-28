package com.project.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.Entity.Candidates;
import com.project.Entity.Customers;
import com.project.Entity.User;
import com.project.Repository.CandidateRepository;
import com.project.Repository.CustomerRepository;
import com.project.Repository.UserRepository;
import com.project.model.AuthResponseDto;
import com.project.model.LoginRequestDto;
import com.project.model.RegisterCandidateRequestDto;
import com.project.model.RegisterCustomerRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final CandidateRepository candidateRepository;
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  public  AuthService (JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CandidateRepository candidateRepository, CustomerRepository customerRepository){
      this.candidateRepository = candidateRepository;
	this.customerRepository = customerRepository;
	this.jwtService = jwtService;
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
  public AuthResponseDto register(RegisterCandidateRequestDto request) {
    var user = User.builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .role(request.getRole())
      .build();
    var candidate = Candidates.builder()
    .firstName(request.getFirstName())
    .lastName(request.getLastName())
    .resumeLink(request.getResumeLink())
    .build();
    userRepository.save(user);
    candidateRepository.save(candidate);
    var jwt = jwtService.generateToken(user);
    return AuthResponseDto.builder()
      .token(jwt)
      .build();
  }
  
  public AuthResponseDto registerCustomer(RegisterCustomerRequestDto request) {
	  var user = User.builder()
		      .email(request.getEmail())
		      .password(passwordEncoder.encode(request.getPassword()))
		      .role(request.getRole())
		      .build();
	    var c = Customers.builder()
	    .name(request.getName())
	    .email(request.getEmail())
	     .build();
	    userRepository.save(user);
	    customerRepository.save(c);
	    var jwt = jwtService.generateToken(user);
	    return AuthResponseDto.builder()
	      .token(jwt)
	      .build();
	  }
  
  public void logout(String token) {
      jwtService.revokeToken(token);
	    SecurityContextHolder.clearContext();  
      // Vous pouvez également gérer d'autres actions de déconnexion, telles que l'invalidation de la session,
      // la révocation des jetons, etc., en fonction de vos besoins.
  }
}
