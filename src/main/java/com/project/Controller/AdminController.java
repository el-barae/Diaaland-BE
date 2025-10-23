package com.project.Controller;

import com.project.Service.AuthService;
import com.project.model.AuthResponseDto;
import com.project.model.RegisterAdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.Repository.AdminRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/admin")
public class AdminController {
	private AdminRepository adminRepo;
    private final AuthService authService;
	
	@Autowired
    public void setAdminRepo(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerAdmin(@RequestBody RegisterAdminRequestDto registerRequestDto) {
        return ResponseEntity.ok(authService.registerAdmin(registerRequestDto));
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
