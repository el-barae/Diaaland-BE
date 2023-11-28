package com.project.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.Links;
import com.project.Repository.LinkRepository;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Links> getAllLinks() {
        return linkRepository.findAll();
    }

    public Links getLinkById(Long id) {
        return linkRepository.findById(id).orElse(null);
    }

    public Links createLink(Links link) {
        return linkRepository.save(link);
    }

    public Links updateLink(Long id, Links link) {
        if (linkRepository.existsById(id)) {
            link.setId(id);
            return linkRepository.save(link);
        }
        return null;
    }

    public boolean deleteLink(Long id) {
        if (linkRepository.existsById(id)) {
            linkRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Add other methods as needed for link management
}
