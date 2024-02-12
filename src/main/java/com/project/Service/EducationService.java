package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.Educations;
import com.project.Repository.EducationRepository;
import com.project.Repository.LinkRepository;



@Service
public class EducationService {
    private final EducationRepository educationRepository;
    private final LinkRepository linkRepository;

    public EducationService(EducationRepository educationRepository, LinkRepository linkRepository) {
        this.educationRepository = educationRepository;
        this.linkRepository = linkRepository;
    }

    public List<Educations> getAllEducations() {
        return educationRepository.findAll();
    }

    public Educations getEducationById(Long id) {
        return educationRepository.findById(id).orElse(null);
    }
    
    public List<Educations> getEducationByCandidateId(Long candidateId) {
        return educationRepository.findByCandidateId(candidateId);
    }

    public Educations createEducation(Educations education) {
        return linkRepository.save(education);
    }

    public Educations updateEducation(Long id, Educations education) {
        if (educationRepository.existsById(id)) {
            //education.setId(id);
            return educationRepository.save(education);
        }
        return null;
    }

    public boolean deleteEducation(Long id) {
        if (educationRepository.existsById(id)) {
            educationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for education management
}
