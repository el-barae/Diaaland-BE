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
import com.project.Entity.Candidates;
import com.project.Entity.CandidatesJobs;
import com.project.Entity.Jobs;
import com.project.Service.CandidateJobsService;

@RestController
@RequestMapping("/candidate-jobs")
public class CandidateJobController {
    private final CandidateJobsService candidateJobsService;

    public CandidateJobController(CandidateJobsService candidateJobsService) {
        this.candidateJobsService = candidateJobsService;
    }

    @GetMapping
    public List<CandidatesJobs> getAllCandidateJobs() {
        return candidateJobsService.getAllCandidateJobs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatesJobs> getCandidateJobById(@PathVariable Long id) {
        CandidatesJobs candidateJob = candidateJobsService.getCandidateJobById(id);
        if (candidateJob != null) {
            return ResponseEntity.ok(candidateJob);
        }
        return ResponseEntity.notFound().build();
    }
    
    
    @GetMapping("/byCandidate/{id}")
    public ResponseEntity<List<Jobs>> findJobsByCandidate(@PathVariable Long id) {
        List<Jobs> jobs = candidateJobsService.findJobsByCandidateId(id);

        if (jobs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(jobs);
        }
    }
    
    @GetMapping("/byJob/{id}")
    public ResponseEntity<List<Candidates>> findCandidatesByJob(@PathVariable Long id) {
        List<Candidates> candidates = candidateJobsService.findCandidatesByJobId(id);

        if (candidates.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(candidates);
        }
    }
    
    @PostMapping
    public ResponseEntity<CandidatesJobs> createCandidateJob(@RequestBody CandidatesJobs candidateJob) {
        CandidatesJobs newCandidateJob = candidateJobsService.createCandidateJob(candidateJob);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCandidateJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidatesJobs> updateCandidateJob(@PathVariable Long id, @RequestBody CandidatesJobs candidateJob) {
        CandidatesJobs updatedCandidateJob = candidateJobsService.updateCandidateJob(id, candidateJob);
        if (updatedCandidateJob != null) {
            return ResponseEntity.ok(updatedCandidateJob);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidateJob(@PathVariable Long id) {
        boolean deleted = candidateJobsService.deleteCandidateJob(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
