package com.andretavares.testesecurity.services.amazon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;

@Service
public class AmazonService {

    @Autowired
    private AmazonS3 amazonS3;
    
    public PutObjectResult putObject(String file_path,String bucketName,String keyName,Path targetLocation) throws IOException{

        System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucketName);

        File file = new File(targetLocation.toUri());

        try {
            PutObjectResult putObject = amazonS3.putObject(bucketName,keyName,file);
            return putObject;
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            return null;
        }
    }

}
