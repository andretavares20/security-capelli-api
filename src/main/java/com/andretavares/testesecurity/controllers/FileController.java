package com.andretavares.testesecurity.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andretavares.testesecurity.dto.UploadFileResponse;
import com.andretavares.testesecurity.services.FileService;
import com.andretavares.testesecurity.services.FileStorageService;
import com.google.common.net.HttpHeaders;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileService fileService;

    @Operation( // Swagger/OpenAPI 3.x annotation to describe the endpoint
            summary = "Small summary of the end-point", description = "A detailed description of the end-point")

    @PostMapping(value = "/upload-image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } // Note the consumes in the
                                                                                             // mapping
    )
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {

        return fileService.uploadFile(file);

    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
