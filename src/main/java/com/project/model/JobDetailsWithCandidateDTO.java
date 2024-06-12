package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDetailsWithCandidateDTO {
    private int idCandidate;
    private List<String> skills;
    private List<String> educations;
    private List<JobDetails> jobsDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JobDetails {
        private int idJob;
        private String jobDescription;
    }
}
