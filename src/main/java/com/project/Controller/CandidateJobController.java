package com.project.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.project.Service.CandidateService;
import com.project.Service.FileStorageService;
import com.project.model.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.Entity.Candidates;
import com.project.Entity.CandidatesJobs;
import com.project.Entity.Jobs;
import com.project.Repository.CandidateJobRepository;
import com.project.Service.CandidateJobsService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/jobs/candidate-jobs")
public class CandidateJobController {
    private final CandidateJobsService candidateJobsService;
    private final CandidateJobRepository candidatesJobsRepository;
    private final CandidateService candidateService;
    private final FileStorageService fileStorageService;

    public CandidateJobController(CandidateJobsService candidateJobsService, CandidateJobRepository candidatesJobsRepository, CandidateService candidateService, FileStorageService fileStorageService) {
        this.candidateJobsService = candidateJobsService;
        this.candidatesJobsRepository = candidatesJobsRepository;
        this.candidateService = candidateService;
        this.fileStorageService = fileStorageService;
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
    
    @GetMapping("/byCustomer/{id}")
    public ResponseEntity<List<Candidates>> findCandidatesByCustomer(@PathVariable Long id) {
        List<Candidates> candidates = candidateJobsService.findCandidatesByCustomerId(id);

        if (candidates.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(candidates);
        }
    }

    @GetMapping("/candidatejobsByCustomer/{id}")
    public ResponseEntity<List<CandidatesJobs>> findCandidatesJobsByCustomer(@PathVariable Long id) {
        List<CandidatesJobs> candidatesJobs = candidateJobsService.findCandidatesJobsByCustomerId(id);

        if (candidatesJobs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(candidatesJobs);
        }
    }
    
    @GetMapping("/itsApplied/{candidateId}/{jobId}")
    public boolean isApplied(@PathVariable Long candidateId, @PathVariable Long jobId) {
        return candidatesJobsRepository.existsByCandidateIdAndJobId(candidateId, jobId);
    }

    @GetMapping("/cv/{id}")
    public ResponseEntity<Resource> getResumeFile(@PathVariable Long id) {
        try {
            CandidatesJobs apply = candidateJobsService.getCandidateJobById(id);
            String resumeLink = "/home/el-barae/Documents/Intellij-projects/Diaaland-BE/"+apply.getCv();
            if (resumeLink == null || resumeLink.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Resource resource = fileStorageService.loadFileAsResource(resumeLink);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/diploma/{id}")
    public ResponseEntity<Resource> getDiplomeFile(@PathVariable Long id) {
        try {
            CandidatesJobs apply = candidateJobsService.getCandidateJobById(id);
            String resumeLink = "/home/el-barae/Documents/Intellij-projects/Diaaland-BE/"+apply.getDiploma();
            if (resumeLink == null || resumeLink.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Resource resource = fileStorageService.loadFileAsResource(resumeLink);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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

    @PutMapping("/status/accept/{id}")
    public ResponseEntity<CandidatesJobs> acceptStatus(@PathVariable Long id) {
        boolean updated = candidateJobsService.setStatus(id,"accept");
        if (updated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/status/refuse/{id}")
    public ResponseEntity<CandidatesJobs> refuseStatus(@PathVariable Long id) {
        boolean updated = candidateJobsService.setStatus(id,"refuse");
        if (updated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/send")
    public ResponseEntity<CandidatesJobs> createCandidateJob(
            @RequestParam("candidateId") Long candidateId,
            @RequestParam("jobId") Long jobId,
            @RequestParam("cv") MultipartFile cvFile,
            @RequestParam("diploma") MultipartFile diplomaFile,
            @RequestParam("coverLetter") String coverLetter) throws IOException {

        CandidatesJobs candidateJob = candidateJobsService.saveCandidateJob(candidateId, jobId, cvFile, diplomaFile, coverLetter);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(candidateJob.getId())
                .toUri();

        return ResponseEntity.created(location).body(candidateJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidateJob(@PathVariable Long id) {
        boolean deleted = candidateJobsService.deleteCandidateJob(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{candidateId}/{jobId}")
    public ResponseEntity<Void> deleteCandidateJobByCandidateAndJob(
            @PathVariable Long candidateId,
            @PathVariable Long jobId) {

        boolean deleted = candidateJobsService.deleteCandidateJobByCandidateAndJob(candidateId, jobId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
