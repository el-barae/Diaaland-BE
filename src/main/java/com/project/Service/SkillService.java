package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.Skills;
import com.project.Repository.SkillRepository;


@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skills> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skills getSkillById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    public Skills createSkill(Skills skill) {
        return skillRepository.save(skill);
    }

    public Skills updateSkill(Long id, Skills skill) {
        if (skillRepository.existsById(id)) {
            skill.setId(id);
            return skillRepository.save(skill);
        }
        return null;
    }

    public boolean deleteSkill(Long id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for skill management
}

