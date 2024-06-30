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
public class CandidateDetailsWithJobDTO {
    private Long jobId;
    private String jobDescription;

    @Convert(converter = DegreesConverter.class)
    private List<String> degrees;

    private List<CandidateDetails> candidatesDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CandidateDetails {
        private Long candidateId;
        private List<String> skills;
        private List<String> educations;
    }
}

