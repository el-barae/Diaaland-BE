package com.project.Entity;

import jakarta.persistence.Entity;

@Entity
public class Other_Links extends Links{
	public Other_Links(String name, String url, String description, Candidates candidate) {
    	super(name, url, description, candidate);
    }
	
	public Other_Links() {
    }
	
}
