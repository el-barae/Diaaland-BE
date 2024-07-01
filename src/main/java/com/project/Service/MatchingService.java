package com.project.Service;

import com.project.Entity.*;
import com.project.Repository.*;
import com.project.model.CandidateDetailsWithJobDTO;
import com.project.model.JobDetailsWithCandidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

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
    private CustomerRepository customerRepository;

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

    public List<Matching> getAllMatching() {
        return matchingRepository.findAll();
    }

    public List<Matching> getMatchingByCandidate(Long candidateId) {
        Candidates candidate = candidateRepository.findById(candidateId).orElse(null);
        if (candidate == null) {
            return null;
        }
        else{
            return matchingRepository.findMatchingByCandidateId(candidateId);
        }
    }

    public List<Matching> getMatchingByCustomer(Long customerId) {
        Customers customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            return null;
        }
        else{
            return matchingRepository.findMatchingByCustomerId(customerId);
        }
    }

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
        input.put("degrees", job.getDegrees());
        input.put("candidatesDetails", candidatesDetails);

        // Call the Python API to get matching scores
        List<Map<String, Object>> matchingScores;
        try {
            matchingScores = pythonApiService.getMatchingScoresByJob(input);
        } catch (HttpServerErrorException e) {
            System.err.println("Received 500 Internal Server Error: " + e.getMessage());
            // Optionally, log the error details or send an alert
            e.printStackTrace();
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("An error occurred while calling the Python API: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }

        // Process the response
        if (matchingScores == null || matchingScores.isEmpty()) {
            System.err.println("Failed to get matching scores from the Python API.");
            return Collections.emptyList();
        }

        // Merge matching scores into the response
        Map<Long, Double> matchingScoresMap = matchingScores.stream()
                .collect(Collectors.toMap(
                        score -> Long.valueOf((Integer) score.get("idCandidate")),
                        score -> ((Number) score.get("matchingScore")).doubleValue()
                ));

        List<Matching> matchings = new ArrayList<>();
        candidates.forEach(candidate -> {
            Long candidateId = candidate.getId();
            Double score = matchingScoresMap.getOrDefault(candidateId, 0.0);

            if (score > 0.3) {
                Matching matching = new Matching();
                matching.setJob(job);
                matching.setCandidate(candidate);
                matching.setScore(score);

                matchings.add(matching);
            }
        });

        if (!matchings.isEmpty()) {
            matchingRepository.saveAll(matchings);
        }

        return matchings;
    }


    public List<Matching> getJobDescriptionAndCandidateDetails(Long candidateId) {
        // Fetch candidate details
        Candidates candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // Collect skills and educations for the candidate
        List<String> skills = candidateSkillRepository.findSkillsByCandidateId(candidateId).stream()
                .map(Skills::getName)
                .collect(Collectors.toList());
        List<String> educations = educationRepository.findByCandidateId(candidateId).stream()
                .map(Educations::getName)
                .collect(Collectors.toList());

        // Fetch all jobs
        List<Jobs> jobs = jobRepository.findAll();
        List<Map<String, Object>> jobDetailsList = jobs.stream()
                .map(job -> {
                    Map<String, Object> jobMap = new HashMap<>();
                    jobMap.put("jobId", job.getId());
                    jobMap.put("description", job.getDescription());
                    jobMap.put("degrees", job.getDegrees() != null ? job.getDegrees() : Collections.emptyList());
                    return jobMap;
                }).collect(Collectors.toList());

        // Prepare input for Python API in the required JSON format
        Map<String, Object> input = new HashMap<>();
        input.put("candidateId", candidate.getId());
        input.put("skills", skills != null ? skills : Collections.emptyList());
        input.put("educations", educations != null ? educations : Collections.emptyList());
        input.put("jobsDetails", jobDetailsList);

        // Log or print the input JSON for debugging
        System.out.println("Input JSON: " + input);

        // Call the Python API to get matching scores
        List<Map<String, Object>> matchingScores;
        try {
            matchingScores = pythonApiService.getMatchingScoresByCandidate(input);
        } catch (Exception e) {
            System.err.println("Error while calling Python API: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }

        // Validate the API response
        if (matchingScores == null || matchingScores.isEmpty()) {
            System.err.println("Failed to get matching scores from the Python API.");
            return Collections.emptyList();
        }

        // Handle duplicate keys by merging scores, keeping the maximum score
        Map<Long, Map<Long, Double>> matchingScoresMap = matchingScores.stream()
                .collect(Collectors.groupingBy(
                        score -> Long.valueOf((Integer) score.get("idJob")),
                        Collectors.toMap(
                                score -> Long.valueOf((Integer) score.get("idCandidate")),
                                score -> ((Number) score.get("matchingScore")).doubleValue(),
                                Double::max  // Handle duplicates by keeping the maximum score
                        )
                ));

        List<Matching> matchings = new ArrayList<>();
        for (Jobs job : jobs) {
            Map<Long, Double> candidateScores = matchingScoresMap.get(job.getId());
            if (candidateScores != null) {
                for (Map.Entry<Long, Double> entry : candidateScores.entrySet()) {
                    Long candidateMatchId = entry.getKey();
                    Double score = entry.getValue();

                    if (score > 0.3) {
                        Matching matching = new Matching();
                        matching.setJob(job);
                        Candidates matchedCandidate = candidateRepository.findById(candidateMatchId)
                                .orElse(null);
                        if (matchedCandidate != null) {
                            matching.setCandidate(matchedCandidate);
                            matching.setScore(score);
                            matchings.add(matching);
                        }
                    }
                }
            }
        }

        if (!matchings.isEmpty()) {
            matchingRepository.saveAll(matchings);
        }

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

    @Transactional
    public void removeDuplicateMatchings() {
        // Find all duplicates
        List<Matching> duplicateMatchings = matchingRepository.findDuplicates();

        // Group by candidate and job IDs to find duplicates
        Map<String, List<Matching>> groupedMatchings = duplicateMatchings.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getCandidate().getId() + "-" + m.getJob().getId()
                ));

        // Collect IDs to keep one and delete the rest
        List<Long> idsToKeep = new ArrayList<>();
        List<Long> idsToDelete = new ArrayList<>();

        groupedMatchings.forEach((key, matchings) -> {
            if (matchings.size() > 1) {
                // Sort by ID and keep the smallest one
                matchings.sort(Comparator.comparing(Matching::getId));
                idsToKeep.add(matchings.get(0).getId());
                idsToDelete.addAll(matchings.stream().skip(1).map(Matching::getId).collect(Collectors.toList()));
            }
        });

        // Delete duplicate matchings
        if (!idsToDelete.isEmpty()) {
            matchingRepository.deleteByIdNotIn(idsToKeep);
        }
    }

}
