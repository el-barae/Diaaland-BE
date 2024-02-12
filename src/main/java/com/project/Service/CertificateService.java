package com.project.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.project.Repository.CertificateRepository;
import com.project.Repository.LinkRepository;
import com.project.Entity.Certificates;

@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final LinkRepository linkRepository;

    public CertificateService(CertificateRepository certificateRepository, LinkRepository linkRepository) {
        this.certificateRepository = certificateRepository;
        this.linkRepository = linkRepository;
    }

    public List<Certificates> getAllCertificates() {
        return certificateRepository.findAll();
    }

    public Certificates getCertificateById(Long id) {
        return certificateRepository.findById(id).orElse(null);
    }
    
    public List<Certificates> getCertificatesByCandidateId(Long candidateId) {
        return certificateRepository.findByCandidateId(candidateId);
    }

    public Certificates createCertificate(Certificates certificate) {
        return linkRepository.save(certificate);
    }

    public Certificates updateCertificate(Long id, Certificates certificate) {
        if (certificateRepository.existsById(id)) {
            certificate.setId(id);
            return certificateRepository.save(certificate);
        }
        return null;
    }

    public boolean deleteCertificate(Long id) {
        if (certificateRepository.existsById(id)) {
            certificateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for certificate management
}
