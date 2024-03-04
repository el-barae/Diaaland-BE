package com.project.Entity;

public enum Role {
  USER, ADMIN, CANDIDAT, CUSTOMER;
  
  public static Role getDefaultRole() {
      return USER;
  }
}
