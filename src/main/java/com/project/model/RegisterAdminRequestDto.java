package com.project.model;

import com.project.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAdminRequestDto {
    private String name;
    private String email;
    private String password;
    private Role role;
}
