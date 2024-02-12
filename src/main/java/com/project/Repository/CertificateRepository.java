package com.project.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Certificates;

public interface CertificateRepository extends JpaRepository<Certificates, Long> {
	List<Certificates> findByCandidateId(Long candidateId);
}
