package com.project.Controller;

import com.project.Entity.Candidates;
import com.project.Repository.CandidateRepository;
import com.project.Service.CandidateService;
import com.project.Service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateRepository candidateRepository;

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
}
