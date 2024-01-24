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
import com.project.Entity.Experiences;
import com.project.Service.ExperienceService;

@RestController
@RequestMapping("/api/v1/experiences")
public class ExperienceController {
    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public List<Experiences> getAllExperiences() {
        return experienceService.getAllExperiences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experiences> getExperienceById(@PathVariable Long id) {
        Experiences experience = experienceService.getExperienceById(id);
        if (experience != null) {
            return ResponseEntity.ok(experience);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/byCandidate/{id}")
    public ResponseEntity<List<Experiences>> getXpByCandidateId(@PathVariable Long id) {
    	List <Experiences> xp = experienceService.getXpByCandidateId(id);
    	if (xp.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(xp);
        }
    }

    @PostMapping
    public ResponseEntity<Experiences> createExperience(@RequestBody Experiences experience) {
        Experiences newExperience = experienceService.createExperience(experience);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExperience);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experiences> updateExperience(@PathVariable Long id, @RequestBody Experiences experience) {
        Experiences updatedExperience = experienceService.updateExperience(id, experience);
        if (updatedExperience != null) {
            return ResponseEntity.ok(updatedExperience);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        boolean deleted = experienceService.deleteExperience(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add other methods as needed for experience management
}
