package com.project.Service;

import com.project.Entity.*;
import com.project.Repository.*;
import com.project.model.CandidateDetailsWithJobDTO;
import com.project.model.JobDetailsWithCandidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private MatchingRepository matchingRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;
    @Autowired
    private JobService jobService;

    @Autowired
    private PythonApiService pythonApiService;

    public List<Matching> getJobDetailsWithCandidates(Long jobId) {
        Jobs job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        List<Candidates> candidates = candidateRepository.findAll();

        List<Map<String, Object>> candidatesDetails = candidates.stream().map(candidate -> {
            List<String> skills = collect(candidateSkillRepository.findSkillsByCandidateId(candidate.getId()), Skills::getName);
            List<String> educations = collect(educationRepository.findByCandidateId(candidate.getId()), Educations::getName);

            Map<String, Object> candidateMap = new HashMap<>();
            candidateMap.put("candidateId", candidate.getId());
            candidateMap.put("skills", skills);
            candidateMap.put("educations", educations);
            return candidateMap;
        }).collect(Collectors.toList());

        // Prepare the input for the Python API
        Map<String, Object> input = new HashMap<>();
        input.put("jobId", job.getId());
        input.put("jobDescription", job.getDescription());
        input.put("jobDegrees", job.getDegrees());
        input.put("candidatesDetails", candidatesDetails);

        // Call the Python API to get matching scores
        List<Map<String, Object>> matchingScores;
        try {
            matchingScores = pythonApiService.getMatchingScoresByJob(input);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the error appropriately
            return Collections.emptyList();
        }

        // Merge matching scores into the response
        Map<Long, Double> matchingScoresMap = matchingScores.stream()
                .collect(Collectors.toMap(
                        score -> Long.valueOf((Integer) score.get("idCandidate")),
                        score -> ((Number) score.get("matchingScore")).doubleValue()
                ));

        // Create and save matching records
        List<Matching> matchings = new ArrayList<>();
        candidates.forEach(candidate -> {
            Long candidateId = candidate.getId();
            Double score = matchingScoresMap.getOrDefault(candidateId, 0.0);

            Matching matching = new Matching();
            matching.setJob(job);
            matching.setCandidate(candidate);
            matching.setScore(score);

            matchings.add(matching);
        });

        // Save all matching records to the database
        matchingRepository.saveAll(matchings);

        return matchings;
    }

    public List<Matching> getJobDescriptionAndCandidateDetails(Long candidateId) {
        Candidates candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        List<String> skills = collect(candidateSkillRepository.findSkillsByCandidateId(candidateId), Skills::getName);
        List<String> educations = collect(educationRepository.findByCandidateId(candidateId), Educations::getName);

        List<Jobs> jobs = jobRepository.findAll();
        List<Map<String, Object>> jobDetailsList = jobs.stream()
                .map(job -> {
                    Map<String, Object> jobMap = new HashMap<>();
                    jobMap.put("idJob", job.getId());
                    jobMap.put("jobDescription", job.getDescription());
                    jobMap.put("jobDegrees", job.getDegrees());
                    return jobMap;
                }).collect(Collectors.toList());

        // Prepare the input for the Python API
        Map<String, Object> input = new HashMap<>();
        input.put("idCandidate", candidate.getId());
        input.put("skills", skills != null ? skills : Collections.emptyList());
        input.put("educations", educations != null ? educations : Collections.emptyList());
        input.put("jobsDetails", jobDetailsList);

        // Call the Python API to get matching scores
        List<Map<String, Object>> matchingScores;
        try {
            matchingScores = pythonApiService.getMatchingScoresByCandidate(input);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the error appropriately
            System.err.println("Error while calling Python API: " + e.getMessage());
            return Collections.emptyList();
        }

        // Check the response for correctness
        if (matchingScores == null || matchingScores.isEmpty()) {
            System.err.println("Failed to get matching scores from the Python API.");
            return Collections.emptyList();
        }

        // Merge matching scores into the response
        List<Matching> matchings = matchingScores.stream()
                .map(score -> {
                    Long jobId = Long.valueOf((Integer) score.get("idJob"));
                    Double matchScore = ((Number) score.get("matchingScore")).doubleValue();

                    Matching matching = new Matching();
                    matching.setJob(jobs.stream().filter(j -> j.getId().equals(jobId)).findFirst().orElse(null));
                    matching.setCandidate(candidate);
                    matching.setScore(matchScore);

                    return matching;
                })
                .collect(Collectors.toList());

        // Save the matching results to the database
        matchingRepository.saveAll(matchings);

        return matchings;
    }


   /* public JobDetailsWithCandidateDTO getJobDescriptionAndCandidateDetails(Long candidateId) {
        List<Jobs> jobs = jobRepository.findAll();
        Candidates candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        List<String> skills = collect(candidateSkillRepository.findSkillsByCandidateId(candidateId), Skills::getName);
        List<String> educations = collect(educationRepository.findByCandidateId(candidateId), Educations::getName);

        // Filter jobs based on some condition, for example, all jobs for the demo
        List<JobDetailsWithCandidateDTO.JobDetails> jobDetailsList = jobs.stream()
                .map(job -> new JobDetailsWithCandidateDTO.JobDetails(job.getId().intValue(), job.getDescription()))
                .collect(Collectors.toList());

        JobDetailsWithCandidateDTO candidateDetails = new JobDetailsWithCandidateDTO(
                candidate.getId().intValue(),
                skills,
                educations,
                jobDetailsList
        );

        return candidateDetails;
    }*/

    private <T> List<String> collect(List<T> items, Function<T, String> mapper) {
        return items.stream().map(mapper).collect(Collectors.toList());
    }
}
