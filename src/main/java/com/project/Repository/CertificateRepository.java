package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Certificates;




public interface CertificateRepository extends JpaRepository<Certificates, Long> {
}
