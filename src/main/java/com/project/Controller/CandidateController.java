package com.project.Controller;

import java.net.MalformedURLException;
import java.util.List;

import com.project.Service.FileStorageService;
import com.project.model.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Entity.Candidates;
import com.project.Service.CandidateService;

//import DataFaker.DataFaker;


@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final FileStorageService fileStorageService;

    public CandidateController(CandidateService candidateService, FileStorageService fileStorageService) {
        this.candidateService = candidateService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<Candidates> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidates> getCandidateById(@PathVariable Long id) throws MalformedURLException {
        Candidates candidate = candidateService.getCandidateById(id);

        if (candidate == null) {
            return ResponseEntity.notFound().build();
        }
        // Return response
        return ResponseEntity.ok(candidate);
    }

    @GetMapping("/resumefile/{id}")
    public ResponseEntity<Resource> getResumeFile(@PathVariable Long id) {
        try {
            Candidates candidate = candidateService.getCandidateById(id);
            String resumeLink = "/home/el-barae/Documents/Intellij-projects/Diaaland-BE/"+candidate.getResumeLink();
            if (resumeLink == null || resumeLink.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            String upDir = "src/Uploads";
            Resource resource = fileStorageService.loadFileAsResource(resumeLink, upDir);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getImageFile(@PathVariable Long id) {
        try {
            Candidates candidate = candidateService.getCandidateById(id);
            if (candidate == null || candidate.getPhotoLink() == null || candidate.getPhotoLink().isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            String photoLink = "/home/el-barae/Documents/Intellij-projects/Diaaland-BE/" + candidate.getPhotoLink();
            String upDir = "src/Uploads";
            Resource resource = fileStorageService.loadFileAsResource(photoLink, upDir);
            if (resource == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/tostring/{id}")
    public ResponseEntity<String> getCandidateByIdToString(@PathVariable Long id) {
        String s = candidateService.getCandidateById(id).toString();
        if (s != null) {
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/name/{id}")
    public ResponseEntity<String> getNameById(@PathVariable Long id) {
        String FName = candidateService.getCandidateById(id).getFirstName();
        String LName = candidateService.getCandidateById(id).getLastName();
        if (FName != null && LName != null) {
            return ResponseEntity.ok(FName+' '+LName);
        }
        return ResponseEntity.notFound().build();
    }
    
    
   /* @GetMapping("/findIdByEmail/{email}")
    public Long findIdByEmail(@PathVariable String email) {
        Candidates candidate = candidateService.findByEmail(email);

        if (candidate != null) {
            return candidate.getId();
        } else {
            return null;
        }
    }*/

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
    //@PostMapping("fakecandidate")
    /*@PostMapping("fakecandidate")
>>>>>>> 69b6baa (to intellij idea)
    public Candidates createfakeCandidate(Candidates candidate) {
    	Faker faker = new Faker();
    	candidate.setId((long) faker.number().randomDigitNotZero());
        candidate.setFirstName(faker.name().firstName());
        candidate.setLastName(faker.name().lastName());
        candidate.setDescription(faker.lorem().sentence());
       // candidate.setEmail(faker.internet().emailAddress());
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
<<<<<<< HEAD
        return ResponseEntity.status(HttpStatus.CREATED).body(newCandidate);
    }*/
    /*@GetMapping("/generate-fake-candidates")
    public String generateFakeCandidates() {
        dataFaker.FakeCandidate(10); 
        return "Fake candidates generated and saved.";
    }
        return ResponseEntity.status(HttpStatus.CREATED).body(newCandidate);
    }
    @GetMapping("/generate-fake-candidates")
    public String generateFakeCandidates() {
        dataFaker.FakeCandidate(10); 
        return "Fake candidates generated and saved.";
    }*/

}
