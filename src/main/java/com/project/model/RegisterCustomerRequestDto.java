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
public class RegisterCustomerRequestDto {
  private String name;
  private String email;
  private String numberPhone;
  private String password;
  private Role role;

public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Role getRole() {
	return role;
}
public void setRole(Role role) {
	this.role = role;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
}
