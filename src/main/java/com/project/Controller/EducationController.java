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

import Request.EducationAndLinkRequest;


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
    public ResponseEntity<String> createEducationAndLink(@RequestBody EducationAndLinkRequest request) {

        try {
            // Extract education and link from the request
            Educations education = request.getEducation();
            Links link = request.getLink();

            // Save Links entity
            Links createdLinks = linkService.createLink(link);

            // Associate the created Links entity with Educations
            education.setLink(createdLinks);

            // Save Educations entity
            Educations createdEducation = educationService.createEducation(education);

            return new ResponseEntity<>("Education and Link created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating Education and Link.", HttpStatus.INTERNAL_SERVER_ERROR);
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
