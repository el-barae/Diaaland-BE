package com.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
  private UnauthorizedHandler unauthorizedHandler;
  private final LogoutHandler logoutHandler;
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  public SecurityConfig(LogoutHandler logoutHandler, AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter) {
      this.authenticationProvider = authenticationProvider;
      this.logoutHandler = logoutHandler;
      this.jwtAuthFilter = jwtAuthFilter;
  }

    @Bean
    LogoutHandler customLogoutHandler() {
      return new CustomLogoutHandler(); // Remplacez CustomLogoutHandler par votre implémentation
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http.csrf(csrf -> csrf.disable())
      .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**").permitAll()
      //.requestMatchers("/api/v1/**").permitAll()
      .anyRequest().authenticated())
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .logout(logout ->
              logout.logoutUrl("/api/v1/auth/logout")
                      .addLogoutHandler(logoutHandler)
                      .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
      )
      ;
      /*.logout(logout -> logout
              .logoutUrl("/api/v1/auth/logout") // L'URL de déconnexion
              .addLogoutHandler(logoutHandler) // Ajoutez ici votre gestionnaire de déconnexion personnalisé
              .logoutSuccessHandler((request, response, authentication) -> {
                  SecurityContextHolder.clearContext(); // Nettoyer le contexte de sécurité
                  response.setStatus(HttpServletResponse.SC_OK);
                  response.getWriter().flush();
              })
          );
	  */
	  return http.build();
  }
}
