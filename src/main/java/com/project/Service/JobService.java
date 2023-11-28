package com.project.Service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.project.Entity.Jobs;
import com.project.Repository.JobRepository;

@Service
public class JobService {
    private final JobRepository jobRepository;


    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Jobs> getAllJobs() {
        return jobRepository.findAll();
    }

    public Jobs getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public Jobs createJob(Jobs job) {
        return jobRepository.save(job);
    }

    public Jobs updateJob(Long id, Jobs job) {
        if (jobRepository.existsById(id)) {
        	job.setId(id);
            return jobRepository.save(job);
        }
        return null;
    }

    public boolean deleteJob(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for job management
}
