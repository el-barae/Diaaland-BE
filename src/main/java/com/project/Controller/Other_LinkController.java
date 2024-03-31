package com.project.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.Entity.Other_Links;
import com.project.Service.Other_LinkService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/other_links")
public class Other_LinkController {
    private final Other_LinkService otherLinkService;

    public Other_LinkController(Other_LinkService otherLinkService) {
        this.otherLinkService = otherLinkService;
    }

    @GetMapping
    public List<Other_Links> getAllOtherLinks() {
        return otherLinkService.getAllOtherLinks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Other_Links> getOtherLinkById(@PathVariable Long id) {
        Other_Links otherLink = otherLinkService.getOtherLinkById(id);
        if (otherLink != null) {
            return ResponseEntity.ok(otherLink);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/candidate/{candidateId}")
    public List<Other_Links> getOtherLinksByCandidateId(@PathVariable Long candidateId) {
        return otherLinkService.getOtherLinksByCandidateId(candidateId);
    }

    @PostMapping
    public ResponseEntity<Other_Links> createOtherLink(@RequestBody Other_Links otherLink) {
        Other_Links newOtherLink = otherLinkService.createOtherLink(otherLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOtherLink);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Other_Links> updateOtherLink(@PathVariable Long id, @RequestBody Other_Links otherLink) {
        Other_Links updatedOtherLink = otherLinkService.updateOtherLink(id, otherLink);
        if (updatedOtherLink != null) {
            return ResponseEntity.ok(updatedOtherLink);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOtherLink(@PathVariable Long id) {
        boolean deleted = otherLinkService.deleteOtherLink(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add other methods as needed for other link management
}
