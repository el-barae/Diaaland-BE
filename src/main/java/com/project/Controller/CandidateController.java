package com.project.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.project.Entity.Candidates;
import com.project.Service.CandidateService;

//import DataFaker.DataFaker;


@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    private final CandidateService candidateService;


    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public List<Candidates> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidates> getCandidateById(@PathVariable Long id) {
        Candidates candidate = candidateService.getCandidateById(id);
        if (candidate != null) {
            return ResponseEntity.ok(candidate);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/findIdByEmail/{email}")
    public Long findIdByEmail(@PathVariable String email) {
        Candidates candidate = candidateService.findByEmail(email);

        if (candidate != null) {
            return candidate.getId();
        } else {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<Candidates> createCandidate(@RequestBody Candidates candidate) {
        Candidates newCandidate = candidateService.createCandidate(candidate);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCandidate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidates> updateCandidate(@PathVariable Long id, @RequestBody Candidates candidate) {
        Candidates updatedCandidate = candidateService.updateCandidate(id, candidate);
        if (updatedCandidate != null) {
            return ResponseEntity.ok(updatedCandidate);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        boolean deleted = candidateService.deleteCandidate(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    //private DataFaker dataFaker;
    @PostMapping("fakecandidate")
    public Candidates createfakeCandidate(Candidates candidate) {
    	Faker faker = new Faker();
    	candidate.setId((long) faker.number().randomDigitNotZero());
        candidate.setFirstName(faker.name().firstName());
        candidate.setLastName(faker.name().lastName());
        candidate.setDescription(faker.lorem().sentence());
        candidate.setEmail(faker.internet().emailAddress());
        candidate.setPassword(faker.internet().password());
        candidate.setAccountStatus(faker.options().option("Active", "Inactive"));
        candidate.setPhoneNumber(faker.phoneNumber().phoneNumber());
        candidate.setJobStatus(faker.options().option("Searching", "Not Searching"));
        candidate.setLinkedIn(faker.internet().url());
        candidate.setGitHub(faker.internet().url());
        candidate.setPortfolio(faker.internet().url());
        candidate.setBlog(faker.internet().url());
        candidate.setExpectedSalary(faker.number().randomDouble(2, 30000, 100000));
        candidate.setResumeLink(faker.internet().url());
        candidate.setPhotoLink(faker.internet().url());
        candidateService.createCandidate(candidate);
    	return candidate;
    	/*Candidates newCandidate = candidateService.createCandidate(candidate);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCandidate);*/
    }
    /*@GetMapping("/generate-fake-candidates")
    public String generateFakeCandidates() {
        dataFaker.FakeCandidate(10); 
        return "Fake candidates generated and saved.";
    }*/

}
