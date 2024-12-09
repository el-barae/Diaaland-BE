package com.project.Controller;

import com.project.Entity.*;
import com.project.Repository.*;
import com.project.Service.CandidateService;
import com.project.Service.CustomerService;
import com.project.Service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Autowired
    private EducationRepository educationRepository;

    private static final String EXTRACT_API_URL = "http://127.0.0.1:8000/extract";

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // Store the file
            String uploadDir = "src/Uploads";
            Candidates c = candidateService.getCandidateById(id);
            String fileName = fileStorageService.storeFile(file, uploadDir);
            c.setResumeLink(fileName);
            candidateRepository.save(c);
            return ResponseEntity.status(HttpStatus.OK).body(fileName);
        } catch (Exception e) {
            // Handle file upload failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/uploadCV/{id}")
    public ResponseEntity<String> uploadCV(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // Store the file
            String uploadDir = "src/Uploads";
            Candidates c = candidateService.getCandidateById(id);
            String fileName = fileStorageService.storeFile(file, uploadDir);
            c.setResumeLink(fileName);
            candidateRepository.save(c);
            // Call the extract API
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.postForObject(EXTRACT_API_URL, Map.of("file_path", "/home/el-barae/Documents/Intellij-projects/Diaaland-BE/"+c.getResumeLink()), Map.class);

            List<String> extractedSkills = (List<String>) response.get("skills");
            List<String> extractedEducations = (List<String>) response.get("educations");

            // Save skills
            for (String skillName : extractedSkills) {
                Skills skill = skillRepository.findByName(skillName);

                CandidateSkills candidateSkill = new CandidateSkills();
                candidateSkill.setCandidate(c);
                candidateSkill.setSkill(skill);
                candidateSkill.setScore(0);
                candidateSkillRepository.save(candidateSkill);
            }

            // Save educations
            for (String educationName : extractedEducations) {
                Educations education = new Educations();
                education.setName(educationName);
                education.setSchool("School name");
                education.setCandidate(c);
                educationRepository.save(education);
            }

            return ResponseEntity.status(HttpStatus.OK).body(fileName);
        } catch (Exception e) {
            // Handle file upload failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/uploadImg/{id}")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // Store the file
            String uploadDir = "src/Uploads";
            Candidates c = candidateService.getCandidateById(id);
            String fileName = fileStorageService.storeFile(file, uploadDir);
            c.setPhotoLink(fileName);
            candidateRepository.save(c);
            return ResponseEntity.status(HttpStatus.OK).body(fileName);
        } catch (Exception e) {
            // Handle file upload failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/uploadLogo/{id}")
    public ResponseEntity<String> uploadLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // Store the file
            String uploadDir = "src/Uploads";
            Customers c = customerService.getCustomerById(id);
            String fileName = fileStorageService.storeFile(file, uploadDir);
            c.setLogo(fileName);
            customerRepository.save(c);
            return ResponseEntity.status(HttpStatus.OK).body(fileName);
        } catch (Exception e) {
            // Handle file upload failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
}
