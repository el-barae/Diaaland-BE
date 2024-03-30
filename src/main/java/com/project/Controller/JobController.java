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
import com.project.Entity.Customers;
import com.project.Entity.Jobs;
import com.project.Service.CustomerService;
import com.project.Service.JobService;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;
    private final CustomerService customerService;

    public JobController(JobService jobService, CustomerService customerService) {
        this.jobService = jobService;
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public List<Jobs> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jobs> getJobById(@PathVariable Long id) {
        Jobs job = jobService.getJobById(id);
        if (job != null) {
            return ResponseEntity.ok(job);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/byCustomer/{id}")
    public ResponseEntity<List<Jobs>> getJobsByCustomer(@PathVariable Long id) {
    	Customers customer = customerService.getCustomerById(id);
   
        if (customer != null) {
            List<Jobs> jobs = jobService.getJobsByCustomer(customer);
            return ResponseEntity.ok(jobs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Jobs> createJob(@RequestBody Jobs job) {
        Jobs newJob = jobService.createJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(newJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jobs> updateJob(@PathVariable Long id, @RequestBody Jobs job) {
        Jobs updatedJob = jobService.updateJob(id, job);
        if (updatedJob != null) {
            return ResponseEntity.ok(updatedJob);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        boolean deleted = jobService.deleteJob(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
   /* @PostMapping("fakejob")
    public Jobs createfakeJob(Jobs job) {
    Faker faker = new Faker();
	Long id=(long) faker.number().randomDigitNotZero();
    String jobName = faker.job().title();
    String jobDescription = faker.lorem().sentence();
    double jobMinSalary = faker.number().randomDouble(2, 30000, 50000); // Salaire minimum entre 30000 et 50000
    double jobMaxSalary = faker.number().randomDouble(2, (int) jobMinSalary, 80000); // Salaire maximum supérieur au minimum
    String jobType = faker.options().option("Full-Time", "Part-Time", "Contract");
    Date jobOpenDate = faker.date().future(10, TimeUnit.DAYS); // Date d'ouverture dans les 10 jours
    Date jobCloseDate = faker.date().future(20, TimeUnit.DAYS); // Date de fermeture dans les 20 jours
    int jobNumberOfPositions = faker.number().numberBetween(1, 10); // De 1 à 10 positions
    String jobAddress = faker.address().fullAddress();
    String jobRemoteStatus = faker.job().seniority();

    Jobs jobs = new Jobs(id, jobName, jobDescription, jobMinSalary, jobMaxSalary, jobType,
            jobOpenDate, jobCloseDate, jobNumberOfPositions, jobAddress, jobRemoteStatus);
    jobService.createJob(jobs);
    return jobs;
    }*/
}

