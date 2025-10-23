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

import com.project.Entity.Links;
import com.project.Service.LinkService;


@RestController
@RequestMapping("/api/v1/profiles/links")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public List<Links> getAllLinks() {
        return linkService.getAllLinks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Links> getLinkById(@PathVariable Long id) {
        Links link = linkService.getLinkById(id);
        if (link != null) {
            return ResponseEntity.ok(link);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Links> createLink(@RequestBody Links link) {
        Links newLink = linkService.createLink(link);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLink);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Links> updateLink(@PathVariable Long id, @RequestBody Links link) {
        Links updatedLink = linkService.updateLink(id, link);
        if (updatedLink != null) {
            return ResponseEntity.ok(updatedLink);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        boolean deleted = linkService.deleteLink(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add other methods as needed for link management
}
