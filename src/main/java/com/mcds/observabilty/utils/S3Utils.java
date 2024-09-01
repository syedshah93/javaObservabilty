package com.mcds.observabilty.utils;

import com.mcds.observabilty.config.AWSConfig;
import com.mcds.observabilty.services.impl.ObservabilityServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class S3Utils {

    @Autowired
    private AWSConfig awsConfig;

    private final static Logger logger = LogManager.getLogger(ObservabilityServiceImpl.class);

    public void downloadFile(String fileName) {

        logger.info("Downloading file: " + fileName);

        Region region = Region.of(awsConfig.getRegion());
        S3Client s3Client = S3Client.builder().region(region).build();
        String filePath = String.format("%s/%s","workdir/",fileName);
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        createDirectoryIfNotExists(parentDir);

        FileSystemResource fileResource = new FileSystemResource(file);
        try {

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(awsConfig.getBucketName())
                    .key(fileName)
                    .build();

            logger.info("GetObjectRequest : {}", getObjectRequest);

            ResponseInputStream<GetObjectResponse> s3is = s3Client.getObject(getObjectRequest,  ResponseTransformer.toInputStream());
            FileOutputStream fos = new FileOutputStream(fileResource.getFile());
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();

            logger.info("Successfully downloaded file: " + fileName);

        } catch (S3Exception e) {
            logger.error("Error occured while downloading the file from the bucket: {}, fileName {}, awsRegion {}, detailed Error Message: {}", awsConfig.getBucketName(),
                    fileName, awsConfig.getRegion(), e.awsErrorDetails().errorMessage());
            throw e;
        } catch (IOException e) {
           logger.error("System error occured while downloading the file from the bucket: {}, fileName {}, awsRegion {}, detailed Error Message: {}", awsConfig.getBucketName(),
                   fileName, awsConfig.getRegion(), e.getMessage());
           throw new RuntimeException(e);
        }
    }

    static void createDirectoryIfNotExists(File parentDir) {
        if (!parentDir.exists()) {
            boolean created = parentDir.mkdirs(); // Create directory and any necessary but nonexistent parent directories
            if (created) {
                logger.info("Created directory: " + parentDir.getAbsolutePath());
            } else {
                logger.warn("Directory already exists or could not be created: " + parentDir.getAbsolutePath());
            }
        }
    }


}
