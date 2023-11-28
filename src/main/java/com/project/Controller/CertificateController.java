package com.project.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Entity.Certificates;
import com.project.Service.CertificateService;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<Certificates> getAllCertificates() {
        return certificateService.getAllCertificates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificates> getCertificateById(@PathVariable Long id) {
        Certificates certificate = certificateService.getCertificateById(id);
        if (certificate != null) {
            return ResponseEntity.ok(certificate);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Certificates> createCertificate(@RequestBody Certificates certificate) {
        Certificates newCertificate = certificateService.createCertificate(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCertificate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certificates> updateCertificate(@PathVariable Long id, @RequestBody Certificates certificate) {
        Certificates updatedCertificate = certificateService.updateCertificate(id, certificate);
        if (updatedCertificate != null) {
            return ResponseEntity.ok(updatedCertificate);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        boolean deleted = certificateService.deleteCertificate(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add other methods as needed for certificate management
}
