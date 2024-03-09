package com.project.Entity;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
  private static final long serialVersionUID = 1L;
@Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  private String email;
  private String password;
  private String phoneNumber;
  private String description;
  private String adress;
  private String city;
  private String country;
  @Enumerated(EnumType.STRING)
  private Role role;
  
  public User(String email, String password, String phoneNumber, String description, String adress, String city, String country, Role role) {
	    this.email = email;
	    this.password = password;
	    this.phoneNumber = phoneNumber;
	    this.description = description;
	    this.adress = adress;
	    this.city = city;
	    this.country = country;
	    this.role = (role != null) ? role : Role.getDefaultRole();
  	}
  
  public User() {
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }
  
  @Override
  public String getUsername() {
    return email;
  }
  
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void setId(Long id) {
		this.id = id;
	}
  
  public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

public void setPassword(String password) {
	this.password = password;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getAdress() {
	return adress;
}

public void setAdress(String adress) {
	this.adress = adress;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getPhoneNumber() {
	return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}

}
