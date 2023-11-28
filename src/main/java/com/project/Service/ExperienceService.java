package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.Experiences;
import com.project.Repository.ExperianceRepository;


@Service
public class ExperienceService {
    private final ExperianceRepository experienceRepository;

    public ExperienceService(ExperianceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public List<Experiences> getAllExperiences() {
        return experienceRepository.findAll();
    }

    public Experiences getExperienceById(Long id) {
        return experienceRepository.findById(id).orElse(null);
    }

    public Experiences createExperience(Experiences experience) {
        return experienceRepository.save(experience);
    }

    public Experiences updateExperience(Long id, Experiences experience) {
        if (experienceRepository.existsById(id)) {
            experience.setId(id);
            return experienceRepository.save(experience);
        }
        return null;
    }

    public boolean deleteExperience(Long id) {
        if (experienceRepository.existsById(id)) {
            experienceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for experience management
}
