package com.project.Entity;

import jakarta.persistence.*;

@Entity
public class Admin {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
    private String name;
    @Column
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public static Admin.Builder builder() {
        return new Admin.Builder();
    }

    public static class Builder {
        private Admin a = new Admin();
        public Admin.Builder name(String n) {
            a.name = n;
            return this;
        }

        public Admin.Builder email(String n) {
            a.email = n;
            return this;
        }

        public Admin build() {
            return a;
        }
    }

    public Admin(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public Admin() {
    	
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

}
