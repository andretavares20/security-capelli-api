package com.andretavares.testesecurity.services;

import java.io.File;
import java.io.FileOutputStream;
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

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.andretavares.testesecurity.FileStorageProperties;
import com.andretavares.testesecurity.exceptions.FileNotFoundException;
import com.andretavares.testesecurity.exceptions.FileStorageException;
import com.andretavares.testesecurity.services.amazon.AmazonService;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private AmazonService amazonService;

    @Autowired
    public FileStorageService(FileStorageProperties properties) {
        this.fileStorageLocation = Paths.get(properties.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Falha ao criar diretório", e);
        }
    }

    public String storeFile(MultipartFile file, String bucketName) throws IOException {

        String filenameExtension = StringUtils.getFilenameExtension(StringUtils.cleanPath(file.getOriginalFilename()));
        String fileName = UUID.randomUUID().toString() + "." + filenameExtension;

        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        PutObjectResult putObjectResult = amazonService.putObject(filenameExtension, bucketName, fileName,
                targetLocation);
        if (putObjectResult == null) {
            return "";
        }
        return fileName;
    }

    public FileOutputStream downloadObject(String bucketName, String keyName) throws IOException {

        S3Object s3Object = amazonService.getObject(bucketName, keyName);
        S3ObjectInputStream s3is = s3Object.getObjectContent();
        FileOutputStream fos = new FileOutputStream(new File(keyName));
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = s3is.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        s3is.close();
        fos.close();
        if (s3Object == null) {
            return null;
        }
        return fos;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName);
            UrlResource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new FileNotFoundException("Arquivo não encontrado");
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Arquivo não encontrado", e);
        }
    }

}
