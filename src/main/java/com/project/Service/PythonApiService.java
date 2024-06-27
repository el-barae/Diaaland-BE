package com.project.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@Service
public class PythonApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String PYTHON_API_URL = "http://localhost:8000/";

    public List<Map<String, Object>> getMatchingScoresByJob(Map<String, Object> input) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(input), headers);
        ResponseEntity<String> response = restTemplate.exchange(PYTHON_API_URL+"match_resumes", HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return objectMapper.readValue(response.getBody(), List.class);
        } else {
            throw new Exception("Error calling Python API: " + response.getStatusCode());
        }
    }

    public List<Map<String, Object>> getMatchingScoresByCandidate(Map<String, Object> input) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(input), headers);
        ResponseEntity<String> response = restTemplate.exchange(PYTHON_API_URL+"match_jobs", HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return objectMapper.readValue(response.getBody(), List.class);
        } else {
            throw new Exception("Error calling Python API: " + response.getStatusCode());
        }
    }
}
