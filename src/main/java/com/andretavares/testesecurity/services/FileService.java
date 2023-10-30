package com.andretavares.testesecurity.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andretavares.testesecurity.dto.UploadFileResponse;

@Service
public class FileService {

    @Autowired
    private FileStorageService fileStorageService;



    public UploadFileResponse uploadFile(MultipartFile file,String filePath) throws IOException {
        String fileName = fileStorageService.storeFile(file, filePath);

        return new UploadFileResponse(fileName, filePath, file.getContentType(),
                file.getSize());

    }

}
