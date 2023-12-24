package com.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
  private UnauthorizedHandler unauthorizedHandler;
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter) {
      this.authenticationProvider = authenticationProvider;
      this.jwtAuthFilter = jwtAuthFilter;
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
      /*.logout(logout ->
              logout.logoutUrl("/api/v1/auth/logout")
                      .addLogoutHandler(customLogoutHandler())
                      .logoutSuccessHandler(//new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                    		  	(request, response, authentication) -> SecurityContextHolder.clearContext())
                      .permitAll()
      )*/
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
          
          @Bean
     CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // Autorisez les requêtes depuis ce domaine
        configuration.addAllowedMethod("*"); // Autorisez toutes les méthodes
        configuration.addAllowedHeader("*"); // Autorisez tous les en-têtes

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
       
    }
    
    
      @Bean
  AuthenticationFailureHandler authenticationFailureHandler() {
      return (AuthenticationFailureHandler) new CustomAuthenticationFailureHandler();
  }
    
    
    @Bean
    LogoutHandler customLogoutHandler() {
      return new CustomLogoutHandler(); // Remplacez CustomLogoutHandler par votre implémentation
  }
	  */
	  return http.build();
  }
}
