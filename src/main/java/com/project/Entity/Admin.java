package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Admin {
	@Id
	private Long id;
	@Column
    private String name;
    @Column
    private String email;
	
    public Admin(Long id, String name, String email) {
    	this.id=id;
    	this.name=name;
    	this.email=email;
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
