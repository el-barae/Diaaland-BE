package com.project.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Service.AuthService;
import com.project.configuration.CustomAuthenticationFailureHandler;
import com.project.model.AuthResponseDto;
import com.project.model.LoginRequestDto;
import com.project.model.RegisterRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
  public AuthController (AuthService authService) {
      this.authService = authService;
  }

  @PostMapping
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto registerRequestDto) {
	 //customAuthenticationFailureHandler.setUsername(registerRequestDto.getEmail());
    return ResponseEntity.ok(authService.login(registerRequestDto));
  }
  
  @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
    return ResponseEntity.ok(authService.register(registerRequestDto));
  }
  
  /*@PostMapping("/log-out")
  public ResponseEntity<String> logout(HttpServletRequest request) {
      String token = ExtractTokenFromRequest.extractTokenFromRequest(request);
      tokenBlacklist.addToBlacklist(token);

      // Clear any session-related data if necessary

      return ResponseEntity.ok("Logged out successfully");
  }
  @GetMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request,
	      HttpServletResponse response,
	      Authentication authentication) {
      ls.logout(request, response, authentication);
      return ResponseEntity.ok("Logout successful");
  }*/
  /*@GetMapping("/logout")
  public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {        
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null) {
          new SecurityContextLogoutHandler().logout(request, response, auth);
      }
        
      return "redirect:/login?logout";
  }
  */
}

