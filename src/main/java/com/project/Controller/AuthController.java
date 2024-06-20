package com.project.Controller;

import java.util.Map;
import com.project.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Service.AuthService;
import com.project.Service.ForgotPasswordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final ForgotPasswordService forgotPasswordService;

  @PostMapping
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto registerRequestDto) {
    return ResponseEntity.ok(authService.login(registerRequestDto));
  }
  
  @PostMapping("/register/candidate")
    public ResponseEntity<AuthResponseDto> registerCandidate(@RequestBody RegisterCandidateRequestDto registerRequestDto) {
    return ResponseEntity.ok(authService.registerCandidate(registerRequestDto));
  }
  
  @PostMapping("/register/customer")
  public ResponseEntity<AuthResponseDto> registerCustomer(@RequestBody RegisterCustomerRequestDto registerRequestDto) {
  return ResponseEntity.ok(authService.registerCustomer(registerRequestDto));
}
  
  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody Map<String, String> requestBody) {
      String token = requestBody.get("token");
      
      if (token != null && !token.isEmpty()) {
          authService.logout(token);
          return ResponseEntity.ok("Logged out successfully");
      } else {
          return ResponseEntity.badRequest().body("Token not provided");
      }
  }
  
  @PostMapping("/forgot/{email}")
  public ResponseEntity<String> forgotPassword(@PathVariable String email) {
      String token = forgotPasswordService.sendResetPasswordEmail(email);
      if (token == null) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body("Failed to send password reset email. Please try again later.");
      } else {
          return ResponseEntity.ok("Password reset email sent successfully. Token: " + token);
      }
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

