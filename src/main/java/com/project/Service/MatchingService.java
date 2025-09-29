package com.project.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Transactional
    public List<Matching> getJobDetailsWithCandidates(Long jobId) {
        Jobs job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        List<Candidates> candidates = candidateRepository.findAll();

        // Préparer les détails des candidats pour l'API Python
        List<Map<String, Object>> candidatesDetails = candidates.stream().map(candidate -> {
            List<String> skills = collect(candidateSkillRepository.findSkillsByCandidateId(candidate.getId()), Skills::getName);
            List<String> educations = collect(educationRepository.findByCandidateId(candidate.getId()), Educations::getName);

            Map<String, Object> candidateMap = new HashMap<>();
            candidateMap.put("candidateId", candidate.getId());
            candidateMap.put("skills", skills);
            candidateMap.put("educations", educations);
            return candidateMap;
        }).collect(Collectors.toList());

        // Préparer l'input pour l'API Python
        Map<String, Object> input = new HashMap<>();
        input.put("jobId", job.getId());
        input.put("jobDescription", job.getDescription());
        input.put("degrees", job.getDegrees() != null ? job.getDegrees() : Collections.emptyList());
        input.put("candidatesDetails", candidatesDetails);

        // Appel à l'API Python pour obtenir les scores
        List<Map<String, Object>> matchingScores;
        try {
            matchingScores = pythonApiService.getMatchingScoresByJob(input);
        } catch (Exception e) {
            System.err.println("Error calling Python API: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }

        if (matchingScores == null || matchingScores.isEmpty()) {
            System.err.println("No matching scores returned from Python API.");
            return Collections.emptyList();
        }

        // Convertir la réponse en map candidateId -> score
        Map<Long, Double> matchingScoresMap = matchingScores.stream()
                .collect(Collectors.toMap(
                        score -> Long.valueOf((Integer) score.get("idCandidate")),
                        score -> ((Number) score.get("matchingScore")).doubleValue()
                ));

        List<Matching> matchingsToSave = new ArrayList<>();
        for (Candidates candidate : candidates) {
            Long candidateId = candidate.getId();
            Double score = matchingScoresMap.getOrDefault(candidateId, 0.0);

            if (score > 0.1) {
                // Vérifier si un matching existe déjà
                List<Matching> existingMatchings = matchingRepository.findAllByJobIdAndCandidateId(job.getId(), candidateId);

                if (!existingMatchings.isEmpty()) {
                    // Garde le premier et met à jour son score
                    Matching first = existingMatchings.get(0);
                    first.setScore(score);
                    matchingsToSave.add(first);

                    // Supprime les doublons restants
                    if (existingMatchings.size() > 1) {
                        List<Long> idsToDelete = existingMatchings.subList(1, existingMatchings.size())
                                .stream().map(Matching::getId).toList();
                        matchingRepository.deleteAllById(idsToDelete);
                    }
                } else {
                    // Crée un nouveau matching
                    Matching matching = new Matching();
                    matching.setJob(job);
                    matching.setCandidate(candidate);
                    matching.setScore(score);
                    matchingsToSave.add(matching);
                }
            }
        }

        if (!matchingsToSave.isEmpty()) {
            matchingRepository.saveAll(matchingsToSave);
        }

        return matchingsToSave;
    }


    @Transactional
    public List<Matching> getJobDescriptionAndCandidateDetails(Long candidateId) {
        // --- Récupérer le candidat
        Candidates candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // --- Récupérer les skills et educations
        List<String> skills = candidateSkillRepository.findSkillsByCandidateId(candidateId)
                .stream().map(Skills::getName).toList();
        List<String> educations = educationRepository.findByCandidateId(candidateId)
                .stream().map(Educations::getName).toList();

        // --- Récupérer tous les jobs
        List<Jobs> jobs = jobRepository.findAll();
        List<Map<String, Object>> jobDetailsList = jobs.stream().map(job -> {
            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("jobId", job.getId());
            jobMap.put("description", job.getDescription());
            jobMap.put("degrees", job.getDegrees() != null ? job.getDegrees() : Collections.emptyList());
            return jobMap;
        }).toList();

        // --- Préparer le JSON pour FastAPI
        Map<String, Object> input = new HashMap<>();
        input.put("candidateId", candidate.getId());
        input.put("skills", skills);
        input.put("educations", educations);
        input.put("jobsDetails", jobDetailsList);

        List<Map<String, Object>> matchingScores;
        try {
            matchingScores = pythonApiService.getMatchingScoresByCandidate(input);
        } catch (Exception e) {
            throw new RuntimeException("Error while calling Python API", e);
        }

        if (matchingScores == null || matchingScores.isEmpty()) {
            return Collections.emptyList();
        }

        // --- Créer une Map<JobId, Map<CandidateId, Score>> pour un accès rapide
        Map<Long, Map<Long, Double>> matchingScoresMap = matchingScores.stream()
                .collect(Collectors.groupingBy(
                        score -> Long.valueOf((Integer) score.get("idJob")),
                        Collectors.toMap(
                                score -> Long.valueOf((Integer) score.get("idCandidate")),
                                score -> ((Number) score.get("matchingScore")).doubleValue(),
                                Double::max
                        )
                ));

        List<Matching> matchingsToSave = new ArrayList<>();

        for (Jobs job : jobs) {
            Map<Long, Double> candidateScores = matchingScoresMap.get(job.getId());
            if (candidateScores == null) continue;

            for (Map.Entry<Long, Double> entry : candidateScores.entrySet()) {
                Long candidateMatchId = entry.getKey();
                Double score = entry.getValue();

                if (score <= 0.1) continue;

                Candidates matchedCandidate = candidateRepository.findById(candidateMatchId).orElse(null);
                if (matchedCandidate == null) continue;

                // --- Récupérer tous les doublons existants
                List<Matching> existingMatchings = matchingRepository.findAllByJobIdAndCandidateId(job.getId(), candidateMatchId);

                if (!existingMatchings.isEmpty()) {
                    // Mettre à jour le score du premier
                    Matching first = existingMatchings.get(0);
                    first.setScore(score);
                    matchingsToSave.add(first);

                    // Supprimer les doublons restants
                    if (existingMatchings.size() > 1) {
                        List<Matching> toDelete = existingMatchings.subList(1, existingMatchings.size());
                        matchingRepository.deleteAll(toDelete);
                    }
                } else {
                    // --- Créer un nouveau matching
                    Matching matching = new Matching();
                    matching.setJob(job);
                    matching.setCandidate(matchedCandidate);
                    matching.setScore(score);
                    matchingsToSave.add(matching);
                }
            }
        }

        // --- Sauvegarder tous les matchings dans la transaction
        if (!matchingsToSave.isEmpty()) {
            matchingRepository.saveAll(matchingsToSave);
        }

        return matchingsToSave;
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
