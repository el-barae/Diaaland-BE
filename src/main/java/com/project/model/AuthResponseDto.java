package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.project.Entity.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
  private String token;
  private Role role;
  public static Builder builder() {
      return new Builder();
  }
  public static class Builder {
      private final AuthResponseDto authResponse = new AuthResponseDto();

      public Builder token(String token) {
          authResponse.token = token;
          return this;
      }
      
      public Builder role(Role role) {
          authResponse.role = role;
          return this;
      }

      public AuthResponseDto build() {
          return authResponse;
      }
  }
public String getToken() {
	return token;
}
public Role getRole() {
	return role;
}

public void setToken(String token) {
	this.token = token;
}  
}
