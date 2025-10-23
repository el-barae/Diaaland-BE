package com.project.model;

import com.project.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.project.Entity.Role;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String token;
    private Role role;
    private Long id;


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

        public Builder id(Long id) {
            authResponse.id = id;
            return this;
        }


        public AuthResponseDto build () {
            return authResponse;
        }
    }

    public void setToken(String token) {
        this.token = token;
    }
}