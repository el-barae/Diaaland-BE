package com.project.Service;

import org.springframework.stereotype.Service;

import java.util.List;
import com.project.Entity.Candidates;

import com.project.Repository.CandidateRepository;


@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<Candidates> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidates getCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }
    
    public Candidates findByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }

    public Candidates createCandidate(Candidates candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidates updateCandidate(Long id, Candidates candidate) {
        if (candidateRepository.existsById(id)) {
            candidate.setId(id);
            return candidateRepository.save(candidate);
        }
        return null;
    }

    public boolean deleteCandidate(Long id) {
        if (candidateRepository.existsById(id)) {
            candidateRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
