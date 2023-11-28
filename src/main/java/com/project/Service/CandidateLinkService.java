package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.CandidateLinks;
import com.project.Repository.CandidateLinkRepository;


@Service
public class CandidateLinkService {
    private final CandidateLinkRepository candidateLinkRepository;

    public CandidateLinkService(CandidateLinkRepository candidateLinkRepository) {
        this.candidateLinkRepository = candidateLinkRepository;
    }

    public List<CandidateLinks> getAllCandidateLinks() {
        return candidateLinkRepository.findAll();
    }

    public CandidateLinks getCandidateLinkById(Long id) {
        return candidateLinkRepository.findById(id).orElse(null);
    }

    public CandidateLinks createCandidateLink(CandidateLinks candidateLink) {
        return candidateLinkRepository.save(candidateLink);
    }

    public CandidateLinks updateCandidateLink(Long id, CandidateLinks candidateLink) {
        if (candidateLinkRepository.existsById(id)) {
            candidateLink.setId(id);
            return candidateLinkRepository.save(candidateLink);
        }
        return null;
    }

    public boolean deleteCandidateLink(Long id) {
        if (candidateLinkRepository.existsById(id)) {
            candidateLinkRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for candidate link management
}
