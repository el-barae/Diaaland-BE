package com.project.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.project.Entity.Candidates;
import com.project.Entity.CandidatesJobs;
import com.project.Entity.Jobs;
import com.project.Repository.CandidateJobRepository;

@Service
public class CandidateJobsService {
    private final CandidateJobRepository candidateJobsRepository;

    public CandidateJobsService(CandidateJobRepository candidateJobsRepository) {
        this.candidateJobsRepository = candidateJobsRepository;
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
