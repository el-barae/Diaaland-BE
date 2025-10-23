package com.project.Controller;

import com.project.Entity.Candidates;
import com.project.Entity.Matching;
import com.project.Service.MatchingService;
import com.project.model.JobDetailsWithCandidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs/matching")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @GetMapping("byJob/{jobId}")
    public List<Matching> getJobDetailsWithCandidates(@PathVariable Long jobId) {
        return matchingService.getJobDetailsWithCandidates(jobId);
    }

    @GetMapping("byCandidate/{candidateId}")
    public List<Matching> getCandidateWithJobs(@PathVariable Long candidateId) {
        return matchingService.getJobDescriptionAndCandidateDetails(candidateId);
    }

    @GetMapping
    public List<Matching> getAllMatching() {
        return matchingService.getAllMatching();
    }

    @GetMapping("/candidate/{candidateId}")
    public List<Matching> getMatchingByCandidate(@PathVariable Long candidateId) {
        return matchingService.getMatchingByCandidate(candidateId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Matching> getMatchingByCustomer(@PathVariable Long customerId) {
        return matchingService.getMatchingByCustomer(customerId);
    }

    @DeleteMapping("/remove-duplicates")
    public ResponseEntity<String> removeDuplicateMatchings() {
        try {
            matchingService.removeDuplicateMatchings();
            return ResponseEntity.ok("Duplicate matchings removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove duplicate matchings.");
        }
    }
}
