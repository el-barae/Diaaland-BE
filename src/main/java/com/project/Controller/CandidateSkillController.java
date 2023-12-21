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

import com.project.Entity.CandidateSkills;
import com.project.Service.CandidateSkillService;

@RestController
@RequestMapping("/api/v1/candidate-skills")
public class CandidateSkillController {
    private final CandidateSkillService candidateSkillService;

    public CandidateSkillController(CandidateSkillService candidateSkillService) {
        this.candidateSkillService = candidateSkillService;
    }

    @GetMapping
    public List<CandidateSkills> getAllCandidateSkills() {
        return candidateSkillService.getAllCandidateSkills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateSkills> getCandidateSkillById(@PathVariable Long id) {
        CandidateSkills candidateSkill = candidateSkillService.getCandidateSkillById(id);
        if (candidateSkill != null) {
            return ResponseEntity.ok(candidateSkill);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CandidateSkills> createCandidateSkill(@RequestBody CandidateSkills candidateSkill) {
        CandidateSkills newCandidateSkill = candidateSkillService.createCandidateSkill(candidateSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCandidateSkill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateSkills> updateCandidateSkill(@PathVariable Long id, @RequestBody CandidateSkills candidateSkill) {
        CandidateSkills updatedCandidateSkill = candidateSkillService.updateCandidateSkill(id, candidateSkill);
        if (updatedCandidateSkill != null) {
            return ResponseEntity.ok(updatedCandidateSkill);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidateSkill(@PathVariable Long id) {
        boolean deleted = candidateSkillService.deleteCandidateSkill(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add other methods as needed for candidate skill management
}
