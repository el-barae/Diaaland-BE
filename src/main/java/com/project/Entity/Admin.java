package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Admin extends User{
	@Column
    private String fullName;
    @Column
    private String blog;
	
    public Admin(String email, String password, String phoneNumber, String description, String adress, String city, String country, Role role, String name, String blog) {
    	super(email, password, phoneNumber, description, adress, city, country, role);
    	this.fullName=name;
    	this.blog=blog;
    }
    
    public Admin() {
    	
    }

    public String getFullName() {
        return fullName;
    }
    
    public String getBlog() {
        return blog;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }
    
    public void setBlog(String blog) {
        this.blog = blog;
    }

}
