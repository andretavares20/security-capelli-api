package com.andretavares.testesecurity.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andretavares.testesecurity.dto.UploadFileResponse;

@Service
public class FileService {

    @Autowired
    private FileStorageService fileStorageService;

    public UploadFileResponse uploadFile(MultipartFile file, String filePath) throws IOException {
        String fileName = fileStorageService.storeFile(file, filePath);

        return new UploadFileResponse(fileName, filePath, file.getContentType(),
                file.getSize());

    }

    public FileOutputStream downloadObject(String bucketName, String keyName) throws IOException {
        FileOutputStream fileOutputStream = fileStorageService.downloadObject(bucketName, keyName);

        File file = new File(keyName);
        InputStream stream = new FileInputStream(file);
        // MultipartFile multipartFileToSend = new MultipartFile()

        return null;

        // return new UploadFileResponse(fileName, filePath, file.getContentType(),
        // file.getSize());

    }

}
