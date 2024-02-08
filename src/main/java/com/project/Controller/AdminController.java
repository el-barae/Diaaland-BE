package com.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Repository.AdminRepository;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	private AdminRepository adminRepo;
	
	@Autowired
    public void setAdminRepo(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }
	
	@GetMapping("/jobs")
    public int getCountJobs() {
        return adminRepo.countJobs();
    }
	
	@GetMapping("/candidates")
    public int getCountCandidates() {
        return adminRepo.countCandidates();
    }
	
	@GetMapping("/customers")
    public int getCountCustomers() {
        return adminRepo.countCustomers();
    }
}
