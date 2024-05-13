package com.project.Service;

import com.project.Entity.User;
import com.project.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.project.Entity.Candidates;

import com.project.Repository.CandidateRepository;


@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    public CandidateService(CandidateRepository candidateRepository, UserRepository userRepository) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    public List<Candidates> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidates getCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

    public Candidates createCandidate(Candidates candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidates updateCandidate(Long id, Candidates candidate) {
        if (candidateRepository.existsById(id)) {
            candidate.setId(id);
            User user = candidate.getUser();
            user.setEmail(candidate.getEmail());
            Candidates c = candidateRepository.save(candidate);
            userRepository.save(user);
            return c;
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
