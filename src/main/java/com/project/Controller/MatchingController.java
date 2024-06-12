package com.project.Controller;

import com.project.Entity.Matching;
import com.project.Service.MatchingService;
import com.project.model.JobDetailsWithCandidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matching")
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
}
