package com.project.Controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.project.Entity.CandidateLinks;
import com.project.Entity.CandidateSkills;
import com.project.Entity.Candidates;
import com.project.Entity.CandidatesJobs;
import com.project.Entity.Certificates;
import com.project.Entity.Customers;
import com.project.Entity.Educations;
import com.project.Entity.Experiences;
import com.project.Entity.Jobs;
import com.project.Entity.Links;
import com.project.Entity.Projects;
import com.project.Entity.Skills;
import com.project.Service.CandidateJobsService;
import com.project.Service.CandidateLinkService;
import com.project.Service.CandidateService;
import com.project.Service.CandidateSkillService;
import com.project.Service.CertificateService;
import com.project.Service.CustomerService;
import com.project.Service.EducationService;
import com.project.Service.ExperienceService;
import com.project.Service.JobService;
import com.project.Service.LinkService;
import com.project.Service.ProjectService;
import com.project.Service.SkillService;

@RestController
@RequestMapping("/api/v1/fakedata")
public class FakeController {
	private final CandidateService candidateService;
	private final CandidateJobsService candidateJobsService;
	private final JobService jobService;
	private final CandidateLinkService candidateLinkService;
	private final CandidateSkillService candidateSkillService;
	 private final CertificateService certificateService;
	 private final CustomerService customerService;
	 private final EducationService educationService;
	 private final ExperienceService experienceService;
	 private final LinkService linkService;
	 private final ProjectService projectService;
	 private final SkillService skillService;
	public FakeController(CandidateService candidateService,JobService jobService,CandidateJobsService candidateJobsService,CandidateLinkService candidateLinkService,CandidateSkillService candidateSkillService,CertificateService certificateService,CustomerService customerService,EducationService educationService,ExperienceService experienceService,LinkService linkService,ProjectService projectService,SkillService skillService) {
        this.candidateService = candidateService;
		this.candidateJobsService = candidateJobsService;
		this.jobService = jobService;
		this.candidateLinkService = candidateLinkService;
		this.candidateSkillService = candidateSkillService;
		this.certificateService = certificateService;
		this.customerService = customerService;
		this.educationService = educationService;
		this.experienceService = experienceService;
		this.linkService = linkService;
		this.projectService = projectService;
		this.skillService = skillService;
    }
	
	
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
	}
	
	@PostMapping("fakejob")
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
	    }
	
	@PostMapping("fakecandidatejob")
	public CandidatesJobs FakeCandidateJob() {
		Faker faker = new Faker();
		Long id=(long) faker.number().randomDigitNotZero();
        String status = faker.options().option("Applied", "Rejected", "Shortlisted");
        CandidatesJobs candidateJobs = new CandidatesJobs(id,status);
        candidateJobsService.createCandidateJob(candidateJobs);
	    return candidateJobs;
	}
	
	@PostMapping("fakecustomer")
	public Customers FakeCustomer() {
		Faker faker = new Faker();
		Long Id=(long) faker.number().randomDigitNotZero();
        String customerName = faker.company().name();
        String customerAddress = faker.address().streetAddress();
        String customerCity = faker.address().city();
        String customerCountry = faker.address().country();
        String customerDescription = faker.lorem().sentence();
        String customerLogo = faker.internet().url();
        
        Customers customers = new Customers(Id, customerName, customerAddress, customerCity, customerCountry, customerDescription, customerLogo);
        customerService.createCustomer(customers);
	    return customers;
	}
	
	@PostMapping("fakeeducation")
	public Educations FakeEducation() {
		Faker faker = new Faker();
		Long Id=(long) faker.number().randomDigitNotZero();
        String educationName = faker.educator().course();
        String educationSchool = faker.university().name();
        Date educationStartDate = (Date) faker.date().past(10, TimeUnit.DAYS); 
        Date educationEndDate = (Date) faker.date().past(1, TimeUnit.DAYS);
        String educationDiploma = faker.educator().course();
        Educations educations = new Educations(Id, educationName, educationSchool, educationStartDate, educationEndDate, educationDiploma);
        educationService.createEducation(educations);
	    return educations;
	}
	
	@PostMapping("fakeexperience")
	public Experiences FakeExperience() {
		Faker faker = new Faker();
		Long Id=(long) faker.number().randomDigitNotZero();
        String experienceName = faker.job().title();
        Date experienceStartDate = (Date) faker.date().past(10, TimeUnit.DAYS); // Date de début passée dans les 10 jours
        Date experienceEndDate = (Date) faker.date().past(1, TimeUnit.DAYS); // Date de fin passée dans les 1 jours
        
        Experiences experiences = new Experiences(Id, experienceName, experienceStartDate, experienceEndDate);
        experienceService.createExperience(experiences);
	    return experiences;
	}
	
	@PostMapping("fakeproject")
	public Projects FakeProject() {
		Faker faker = new Faker();
		Long Id=(long) faker.number().randomDigitNotZero();
        String projectName = faker.app().name();
        Date projectStartDate = (Date) faker.date().past(2, TimeUnit.DAYS); // Date de début passée dans les 2 ans
        Date projectEndDate = (Date) faker.date().past(1, TimeUnit.DAYS); // Date de fin passée dans l'année précédente
        String projectDescription = faker.lorem().sentence();
        
        Projects projects = new Projects(Id, projectName, projectStartDate, projectEndDate, projectDescription);
        projectService.createProject(projects);
	    return projects;
	}
	
	@PostMapping("fakecandidateskill")
	public CandidateSkills FakeCandidateSkill() {
		Faker faker = new Faker();
		Long Id=(long) faker.number().randomDigitNotZero();
        int score = faker.number().numberBetween(0, 100);
        CandidateSkills candidateSkills = new CandidateSkills(Id, score);
        candidateSkillService.createCandidateSkill(candidateSkills);
	    return candidateSkills;
	}
	
	@PostMapping("fakeskill")
	public Skills FakeSkill() {
		Faker faker = new Faker();
		Long Id=(long) faker.number().randomDigitNotZero();
        String skillName = faker.job().keySkills();
        String skillType = faker.options().option("Technical", "Soft", "Language");
        Skills skills = new Skills(Id, skillName, skillType);
        skillService.createSkill(skills);
	    return skills;
	}
	
	@PostMapping("fakecandidatelink")
	public CandidateLinks FakeCandidateLink() {
		Faker faker = new Faker();
		Long Id=(long) faker.number().randomDigitNotZero();
        CandidateLinks candidateLinks = new CandidateLinks(Id);
        candidateLinkService.createCandidateLink(candidateLinks);
	    return candidateLinks;
	}
	
	@PostMapping("fakelink")
	public Links FakeLink() {
		Faker faker = new Faker();
		Long Id=(long) faker.number().randomDigitNotZero();
        String linkName = faker.internet().domainWord();
        String linkUrl = faker.internet().url();
        Links links = new Links(Id, linkName, linkUrl);
        linkService.createLink(links);
	    return links;
	}
	
	@PostMapping("fakecertificate")
	public Certificates FakeCertificate() {
		Faker faker = new Faker();
		Certificates certificate = new Certificates();
        certificate.setId((long) faker.number().randomDigitNotZero());
        certificate.setName(faker.job().title());
        certificateService.createCertificate(certificate);
	    return certificate;
        }
}
