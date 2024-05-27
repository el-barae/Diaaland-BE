package com.project.Service;

import com.project.model.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String uploadDir = "src/Uploads";

    public String storeFile(MultipartFile file) throws IOException {
        Path copyLocation = Paths
                .get(uploadDir + File.separator + file.getOriginalFilename());
        Files.copy(file.getInputStream(), copyLocation);
        return copyLocation.toString();
    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
            return resource;
        } else {
            throw new ResourceNotFoundException("File not found " + fileName);
        }
    }


}

