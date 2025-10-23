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


import com.project.Entity.CandidateLinks;
import com.project.Service.CandidateLinkService;


@RestController
@RequestMapping("/api/v1/profiles/candidate-links")
public class CandidateLinkController {
    private final CandidateLinkService candidateLinkService;

    public CandidateLinkController(CandidateLinkService candidateLinkService) {
        this.candidateLinkService = candidateLinkService;
    }

    @GetMapping
    public List<CandidateLinks> getAllCandidateLinks() {
        return candidateLinkService.getAllCandidateLinks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateLinks> getCandidateLinkById(@PathVariable Long id) {
        CandidateLinks candidateLink = candidateLinkService.getCandidateLinkById(id);
        if (candidateLink != null) {
            return ResponseEntity.ok(candidateLink);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CandidateLinks> createCandidateLink(@RequestBody CandidateLinks candidateLink) {
        CandidateLinks newCandidateLink = candidateLinkService.createCandidateLink(candidateLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCandidateLink);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateLinks> updateCandidateLink(@PathVariable Long id, @RequestBody CandidateLinks candidateLink) {
        CandidateLinks updatedCandidateLink = candidateLinkService.updateCandidateLink(id, candidateLink);
        if (updatedCandidateLink != null) {
            return ResponseEntity.ok(updatedCandidateLink);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidateLink(@PathVariable Long id) {
        boolean deleted = candidateLinkService.deleteCandidateLink(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add other methods as needed for candidate link management
}
