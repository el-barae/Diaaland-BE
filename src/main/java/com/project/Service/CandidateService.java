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
        Optional<Candidates> optionalCandidate = candidateRepository.findById(id);
        if (optionalCandidate.isPresent()) {
            Candidates existingCandidate = optionalCandidate.get();

            // Update existingCandidate with non-null fields from candidate
            existingCandidate.updateCandidate(candidate);

            // Check if candidate.getUser() is not null before accessing its properties
            if (candidate.getUser() != null) {
                Long uID = candidate.getUser().getId();
                Optional<User> optionalUser = userRepository.findById(uID);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    user.setEmail(candidate.getEmail());
                    userRepository.save(user);
                }
            }

            // Save the updated candidate
            return candidateRepository.save(existingCandidate);
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
