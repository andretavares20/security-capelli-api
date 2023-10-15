package com.andretavares.testesecurity.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.andretavares.testesecurity.FileStorageProperties;
import com.andretavares.testesecurity.exceptions.FileNotFoundException;
import com.andretavares.testesecurity.exceptions.FileStorageException;

@Service
public class FileStorageService {
    
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties properties){
        this.fileStorageLocation = Paths.get(properties.getUploadDir())
            .toAbsolutePath()
            .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Falha ao criar diretório", e);
        }
    }

    public String storeFile(MultipartFile file){
        try {
            String filenameExtension = StringUtils.getFilenameExtension(StringUtils.cleanPath(file.getOriginalFilename()));
            String fileName = UUID.randomUUID().toString() + "." + filenameExtension;
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Não foi possível salvar o arquivo", e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName);
            UrlResource resource = new UrlResource(filePath.toUri());
            if(!resource.exists()){
                throw new FileNotFoundException("Arquivo não encontrado");
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Arquivo não encontrado",e);
        }
    }
    
}
