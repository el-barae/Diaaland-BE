package com.project.Service;

import com.project.Entity.*;
import com.project.Repository.AdminRepository;
import com.project.model.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.Repository.CandidateRepository;
import com.project.Repository.CustomerRepository;
import com.project.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final AdminRepository adminRepository;
  private final CandidateRepository candidateRepository;
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponseDto login(LoginRequestDto request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    var jwt = jwtService.generateToken(user);
    Role role = user.getRole();
    return AuthResponseDto.builder()
      .token(jwt)
      .role(role)
      .build();
  }

    public AuthResponseDto registerAdmin(RegisterAdminRequestDto request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
        userRepository.save(user);
        adminRepository.save(admin);
        var jwt = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .token(jwt)
                .build();
    }
  public AuthResponseDto registerCandidate(RegisterCandidateRequestDto request) {
    var user = User.builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .role(request.getRole())
      .build();
    var candidate = Candidates.builder()
    .firstName(request.getFirstName())
    .lastName(request.getLastName())
    .email(request.getEmail())
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
                .phoneNumber(request.getNumberPhone())
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
  }
}
