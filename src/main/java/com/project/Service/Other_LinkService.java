package com.project.Service;

import org.springframework.stereotype.Service;
import com.project.Entity.Other_Links;
import com.project.Repository.LinkRepository;
import com.project.Repository.Other_linkRepository;

import java.util.List;

@Service
public class Other_LinkService {
    private final Other_linkRepository otherLinkRepository;
    private final LinkRepository linkRepository;

    public Other_LinkService(Other_linkRepository otherLinkRepository, LinkRepository linkRepository) {
        this.otherLinkRepository = otherLinkRepository;
        this.linkRepository = linkRepository;
    }

    public List<Other_Links> getAllOtherLinks() {
        return otherLinkRepository.findAll();
    }

    public Other_Links getOtherLinkById(Long id) {
        return otherLinkRepository.findById(id).orElse(null);
    }

    public List<Other_Links> getOtherLinksByCandidateId(Long candidateId) {
        return otherLinkRepository.findByCandidateId(candidateId);
    }

    public Other_Links createOtherLink(Other_Links otherLink) {
        return linkRepository.save(otherLink);
    }

    public Other_Links updateOtherLink(Long id, Other_Links otherLink) {
        if (otherLinkRepository.existsById(id)) {
            otherLink.setId(id);
            return otherLinkRepository.save(otherLink);
        }
        return null;
    }

    public boolean deleteOtherLink(Long id) {
        if (otherLinkRepository.existsById(id)) {
            otherLinkRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for other link management
}
