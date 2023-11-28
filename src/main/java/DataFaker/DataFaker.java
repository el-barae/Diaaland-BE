package DataFaker;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import com.project.Repository.CandidateJobRepository;
import com.project.Repository.CandidateLinkRepository;
import com.project.Repository.CandidateSkillRepository;
import com.project.Repository.CertificateRepository;
import com.project.Repository.CustomerRepository;
import com.project.Repository.EducationRepository;
import com.project.Repository.ExperianceRepository;
import com.project.Repository.JobRepository;
import com.project.Repository.LinkRepository;
import com.project.Repository.ProjectRepository;
import com.project.Repository.SkillRepository;
import com.project.Service.CandidateService;

public class DataFaker {
	 private static EducationRepository educationRepository;
	 private static CandidateLinkRepository candidateLinkRepository;
	 private static CandidateJobRepository candidateJobsRepository;
	 private static CandidateService candidateService;
	 private static CandidateSkillRepository candidateSkillRepository;
	 private static CertificateRepository certificateRepository;
	 private static CustomerRepository customerRepository;
	 private static ExperianceRepository experienceRepository;
	 private static JobRepository jobRepository;
	 private static LinkRepository linkRepository;
	 private static ProjectRepository projectRepository;
	 private static SkillRepository skillRepository;
		Faker faker = new Faker();
	 
	public void FakeCandidate(int numCandidates) {
		
		for (int i = 0; i < numCandidates; i++) {


        Candidates candidate = new Candidates();
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
        /*
        FakeCandidateJob();
        FakeJob();
        FakeCustomer();
        FakeEducation();
        FakeExperience();
        FakeProject();
        FakeCandidateSkill();
        FakeSkill();
        FakeCandidateLink();
        FakeLink();
        FakeCertificate();
        */
        
        candidateService.createCandidate(candidate);
		}
		}
		public CandidatesJobs FakeCandidateJob() {
			Faker faker = new Faker();
			Long id=(long) faker.number().randomDigitNotZero();
            String status = faker.options().option("Applied", "Rejected", "Shortlisted");
            CandidatesJobs candidateJobs = new CandidatesJobs(id,status);
            return candidateJobsRepository.save(candidateJobs);
		}
		public Jobs FakeJob() {
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
            return jobRepository.save(jobs);
		}
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
            return customerRepository.save(customers);
		}
		public Educations FakeEducation() {
			Faker faker = new Faker();
			Long Id=(long) faker.number().randomDigitNotZero();
            String educationName = faker.educator().course();
            String educationSchool = faker.university().name();
            Date educationStartDate = (Date) faker.date().past(10, TimeUnit.DAYS); 
            Date educationEndDate = (Date) faker.date().past(1, TimeUnit.DAYS);
            String educationDiploma = faker.educator().course();
            Educations educations = new Educations(Id, educationName, educationSchool, educationStartDate, educationEndDate, educationDiploma);
            return educationRepository.save(educations);
		}
		public Experiences FakeExperience() {
			Faker faker = new Faker();
			Long Id=(long) faker.number().randomDigitNotZero();
            String experienceName = faker.job().title();
            Date experienceStartDate = (Date) faker.date().past(10, TimeUnit.DAYS); // Date de début passée dans les 10 jours
            Date experienceEndDate = (Date) faker.date().past(1, TimeUnit.DAYS); // Date de fin passée dans les 1 jours
            
            Experiences experiences = new Experiences(Id, experienceName, experienceStartDate, experienceEndDate);
            return experienceRepository.save(experiences);
		}
		public Projects FakeProject() {
			Faker faker = new Faker();
			Long Id=(long) faker.number().randomDigitNotZero();
            String projectName = faker.app().name();
            Date projectStartDate = (Date) faker.date().past(2, TimeUnit.DAYS); // Date de début passée dans les 2 ans
            Date projectEndDate = (Date) faker.date().past(1, TimeUnit.DAYS); // Date de fin passée dans l'année précédente
            String projectDescription = faker.lorem().sentence();
            
            Projects projects = new Projects(Id, projectName, projectStartDate, projectEndDate, projectDescription);
            return projectRepository.save(projects);
		}
		public CandidateSkills FakeCandidateSkill() {
			Faker faker = new Faker();
			Long Id=(long) faker.number().randomDigitNotZero();
            int score = faker.number().numberBetween(0, 100);
            CandidateSkills candidateSkills = new CandidateSkills(Id, score);
            return candidateSkillRepository.save(candidateSkills);
		}
		public Skills FakeSkill() {
			Faker faker = new Faker();
			Long Id=(long) faker.number().randomDigitNotZero();
            String skillName = faker.job().keySkills();
            String skillType = faker.options().option("Technical", "Soft", "Language");
            Skills skills = new Skills(Id, skillName, skillType);
            return skillRepository.save(skills);
		}
		public CandidateLinks FakeCandidateLink() {
			Faker faker = new Faker();
			Long Id=(long) faker.number().randomDigitNotZero();
            CandidateLinks candidateLinks = new CandidateLinks(Id);
            return candidateLinkRepository.save(candidateLinks);
		}
		public Links FakeLink() {
			Faker faker = new Faker();
			Long Id=(long) faker.number().randomDigitNotZero();
            String linkName = faker.internet().domainWord();
            String linkUrl = faker.internet().url();
            Links links = new Links(Id, linkName, linkUrl);
            return linkRepository.save(links);
		}
		public Certificates FakeCertificate() {
			Faker faker = new Faker();
			Certificates certificate = new Certificates();
            certificate.setId((long) faker.number().randomDigitNotZero());
            certificate.setName(faker.job().title());
            return certificateRepository.save(certificate);
            }
		
        
        /*CandidateService candidateService = new CandidateService(candidateRepository);
		candidateService.createCandidate(candidate);
		int numJobs = faker.number().numberBetween(0, 5);*/

        
           
            
		//}
	
	
	/*public static void main(String[] args) {
		Faker faker = new Faker();
		int numEntries = faker.number().numberBetween(0, 5);
		FakeCandidate(numEntries);
	}*/
	
}
