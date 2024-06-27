package com.project.Service;

import com.project.Entity.*;
import com.project.Repository.*;
import com.project.model.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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

    private final SkillRepository skillRepository;

    private final CandidateSkillRepository candidateSkillRepository;

    private final EducationRepository educationRepository;

    private static final String EXTRACT_API_URL = "http://127.0.0.1:8000/pdf/extract";

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

    @Transactional
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
        Role role = user.getRole();
        userRepository.save(user);
        adminRepository.save(admin);
        var jwt = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .token(jwt)
                .role(role)
                .build();
    }

    @Transactional
  public AuthResponseDto registerCandidate(RegisterCandidateRequestDto request) {
    var user = User.builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .role(request.getRole())
      .build();
      user = userRepository.save(user);
    var candidate = Candidates.builder()
    .firstName(request.getFirstName())
    .lastName(request.getLastName())
    .email(request.getEmail())
    .resumeLink(request.getResumeLink())
            .user(user)
    .build();
    candidateRepository.save(candidate);
      Role role = user.getRole();
    var jwt = jwtService.generateToken(user);
        // Call the extract API
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.postForObject(EXTRACT_API_URL, Map.of("file_path", "/home/el-barae/Documents/Intellij-projects/Diaaland-BE/"+candidate.getResumeLink()), Map.class);

        List<String> extractedSkills = (List<String>) response.get("skills");
        List<String> extractedEducations = (List<String>) response.get("educations");

        // Save skills
        for (String skillName : extractedSkills) {
            Skills skill = skillRepository.findByName(skillName);

            CandidateSkills candidateSkill = new CandidateSkills();
            candidateSkill.setCandidate(candidate);
            candidateSkill.setSkill(skill);
            candidateSkill.setScore(0);
            candidateSkillRepository.save(candidateSkill);
        }

        // Save educations
        for (String educationName : extractedEducations) {
            Educations education = new Educations();
            education.setName(educationName);
            education.setSchool("School name");
            education.setCandidate(candidate);
            educationRepository.save(education);
        }

    return AuthResponseDto.builder()
      .token(jwt)
            .role(role)
      .build();
  }

    @Transactional
  public AuthResponseDto registerCustomer(RegisterCustomerRequestDto request) {
	  var user = User.builder()
		      .email(request.getEmail())
		      .password(passwordEncoder.encode(request.getPassword()))
		      .role(request.getRole())
		      .build();
      user = userRepository.save(user);
	    var c = Customers.builder()
	    .name(request.getName())
	    .email(request.getEmail())
                .phoneNumber(request.getNumberPhone())
                .user(user)
	     .build();
	    customerRepository.save(c);
      Role role = user.getRole();
	    var jwt = jwtService.generateToken(user);
	    return AuthResponseDto.builder()
	      .token(jwt)
                .role(role)
	      .build();
	  }
  
  public void logout(String token) {
      jwtService.revokeToken(token);
	    SecurityContextHolder.clearContext();  
  }
}
