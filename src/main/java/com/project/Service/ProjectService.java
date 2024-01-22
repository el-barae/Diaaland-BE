package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.Projects;
import com.project.Repository.ProjectRepository;


@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Projects> getAllProjects() {
        return projectRepository.findAll();
    }

    public Projects getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }
    
    public List<Projects> getProjectsByCandidateId(Long candidateId) {
        return projectRepository.findByCandidateId(candidateId);
    }

    public Projects createProject(Projects project) {
        return projectRepository.save(project);
    }

    public Projects updateProject(Long id, Projects project) {
        if (projectRepository.existsById(id)) {
            project.setId(id);
            return projectRepository.save(project);
        }
        return null;
    }

    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for project management
}
