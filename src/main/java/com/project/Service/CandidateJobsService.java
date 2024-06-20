package com.project.Service;

import java.io.IOException;
import java.util.List;

import com.project.Repository.CandidateRepository;
import com.project.Repository.JobRepository;
import org.springframework.stereotype.Service;
import com.project.Entity.Candidates;
import com.project.Entity.CandidatesJobs;
import com.project.Entity.Jobs;
import com.project.Repository.CandidateJobRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CandidateJobsService {
    private final CandidateJobRepository candidateJobsRepository;
    private final FileStorageService fileStorageService;
    private final CandidateRepository candidatesRepository;
    private final JobRepository jobsRepository;


    public CandidateJobsService(CandidateJobRepository candidateJobsRepository, FileStorageService fileStorageService, CandidateRepository candidatesRepository, JobRepository jobsRepository) {
        this.candidateJobsRepository = candidateJobsRepository;
        this.fileStorageService = fileStorageService;
        this.candidatesRepository = candidatesRepository;
        this.jobsRepository = jobsRepository;
    }

    public List<CandidatesJobs> getAllCandidateJobs() {
        return candidateJobsRepository.findAll();
    }

    public CandidatesJobs getCandidateJobById(Long id) {
        return candidateJobsRepository.findById(id).orElse(null);
    }

    public CandidatesJobs createCandidateJob(CandidatesJobs candidateJob) {
        return candidateJobsRepository.save(candidateJob);
    }

    public CandidatesJobs saveCandidateJob(Long candidateId, Long jobId, MultipartFile cvFile, MultipartFile diplomaFile, String coverLetter) throws IOException {
        String applyDir = "src/Uploads/applies";
        String cvUrl = fileStorageService.storeFile(cvFile, applyDir);
        String diplomaUrl = fileStorageService.storeFile(diplomaFile, applyDir);

        Candidates candidate = candidatesRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id " + candidateId));

        Jobs job = jobsRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + jobId));

        CandidatesJobs candidateJob = new CandidatesJobs();
        candidateJob.setCv(cvUrl);
        candidateJob.setDiploma(diplomaUrl);
        candidateJob.setCoverLetter(coverLetter);
        candidateJob.setStatus("pending");
        candidateJob.setCandidate(candidate);
        candidateJob.setJob(job);

        return candidateJobsRepository.save(candidateJob);
    }

    public CandidatesJobs updateCandidateJob(Long id, CandidatesJobs candidateJob) {
        if (candidateJobsRepository.existsById(id)) {
            candidateJob.setId(id);
            return candidateJobsRepository.save(candidateJob);
        }
        return null;
    }

    public boolean deleteCandidateJob(Long id) {
        if (candidateJobsRepository.existsById(id)) {
            candidateJobsRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean deleteCandidateJobByCandidateAndJob(Long candidateId, Long jobId) {
        Long id = candidateJobsRepository.findIdByCandidateIdAndJobId(candidateId, jobId);

        if (id != null && candidateJobsRepository.existsById(id)) {
            candidateJobsRepository.deleteById(id);
            return true;
        }

        return false;
    }
    
    public List<Jobs> findJobsByCandidateId(Long candidateId) {
        return candidateJobsRepository.findJobsByCandidateId(candidateId);
    }
    
    public List<Candidates> findCandidatesByJobId(Long candidateId) {
        return candidateJobsRepository.findCandidatesByJobId(candidateId);
    }
    
    public List<Candidates> findCandidatesByCustomerId(Long customerId){
    	return candidateJobsRepository.findCandidatesByCustomerId(customerId);
    }
}
