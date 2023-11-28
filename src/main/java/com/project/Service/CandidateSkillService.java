package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Repository.CandidateSkillRepository;
import com.project.Entity.CandidateSkills;

@Service
public class CandidateSkillService {
    private final CandidateSkillRepository candidateSkillRepository;

    public CandidateSkillService(CandidateSkillRepository candidateSkillRepository) {
        this.candidateSkillRepository = candidateSkillRepository;
    }

    public List<CandidateSkills> getAllCandidateSkills() {
        return candidateSkillRepository.findAll();
    }

    public CandidateSkills getCandidateSkillById(Long id) {
        return candidateSkillRepository.findById(id).orElse(null);
    }

    public CandidateSkills createCandidateSkill(CandidateSkills candidateSkill) {
        return candidateSkillRepository.save(candidateSkill);
    }

    public CandidateSkills updateCandidateSkill(Long id, CandidateSkills candidateSkill) {
        if (candidateSkillRepository.existsById(id)) {
            candidateSkill.setId(id);
            return candidateSkillRepository.save(candidateSkill);
        }
        return null;
    }

    public boolean deleteCandidateSkill(Long id) {
        if (candidateSkillRepository.existsById(id)) {
            candidateSkillRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for candidate skill management
}
