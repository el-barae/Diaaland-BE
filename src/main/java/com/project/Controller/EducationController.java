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

import com.project.Entity.Educations;
import com.project.Entity.Links;
import com.project.Service.EducationService;
import com.project.Service.LinkService;


@RestController
@RequestMapping("/api/v1/educations")
public class EducationController {
    private final EducationService educationService;
    private final LinkService linkService;

    public EducationController(EducationService educationService, LinkService linkService) {
        this.educationService = educationService;
        this.linkService = linkService;
    }

    @GetMapping
    public List<Educations> getAllEducations() {
        return educationService.getAllEducations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Educations> getEducationById(@PathVariable Long id) {
        Educations education = educationService.getEducationById(id);
        if (education != null) {
            return ResponseEntity.ok(education);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Educations> createEducation(@RequestBody Educations education) {
        try {
            // Assuming the link field is already populated in the incoming Educations object
            Links existingLink = education.getLink();
            
            if (existingLink != null && existingLink.getId() != null) {
                // If the link has an ID, it's assumed to be an existing link
                Links savedLink = linkService.getLinkById(existingLink.getId());
                if (savedLink != null) {
                    // Set the saved link in the education object
                    education.setLink(savedLink);
                } else {
                    // Handle the case where the link with the given ID doesn't exist
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                // Handle the case where the link is not provided or doesn't have an ID
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Save the education entity
            Educations createdEducation = educationService.createEducation(education);

            return new ResponseEntity<>(createdEducation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Educations> updateEducation(@PathVariable Long id, @RequestBody Educations education) {
        Educations updatedEducation = educationService.updateEducation(id, education);
        if (updatedEducation != null) {
            return ResponseEntity.ok(updatedEducation);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        boolean deleted = educationService.deleteEducation(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
