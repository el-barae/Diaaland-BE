package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
  private String token;
  public static Builder builder() {
      return new Builder();
  }
  public static class Builder {
      private final AuthResponseDto authResponse = new AuthResponseDto();

      public Builder token(String token) {
          authResponse.token = token;
          return this;
      }

      public AuthResponseDto build() {
          return authResponse;
      }
  }
public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}  
}
