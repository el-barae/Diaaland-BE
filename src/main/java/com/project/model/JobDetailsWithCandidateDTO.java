package com.project.model;

import jakarta.persistence.Convert;
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
    private Long candidateId;
    private List<String> skills;
    private List<String> educations;
    private List<JobDetails> jobsDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JobDetails {
        private Long jobId;
        private String description;

        @Convert(converter = DegreesConverter.class)
        private List<String> degrees;
    }
}
