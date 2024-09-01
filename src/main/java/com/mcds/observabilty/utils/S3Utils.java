package com.mcds.observabilty.utils;

import com.mcds.observabilty.config.AWSConfig;
import com.mcds.observabilty.exception.CustomException;
import com.mcds.observabilty.services.impl.ObservabilityServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(awsConfig.getBucketName())
                    .key(fileName)
                    .build();

            logger.info("GetObjectRequest : {}", getObjectRequest);

            ResponseInputStream<GetObjectResponse> s3is = s3Client.getObject(getObjectRequest,  ResponseTransformer.toInputStream());
            FileOutputStream fos = new FileOutputStream(new File(fileName));
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

}
