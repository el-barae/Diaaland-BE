package com.project.Service;

import com.project.model.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${upload.dir:src/Uploads}")  // valeur par défaut = src/Uploads
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        Path copyLocation = Paths.get(uploadDir).resolve(file.getOriginalFilename()).normalize();

        // Créer le dossier si inexistant
        Files.createDirectories(copyLocation.getParent());

        // Si fichier existe déjà, on écrase (sinon IOException)
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        // On retourne seulement le nom du fichier (à stocker en DB)
        return file.getOriginalFilename();
    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new ResourceNotFoundException("File not found " + fileName);
        }
    }
}


