package com.mcds.observabilty.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@Data
public class AWSConfig {
    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("{cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("{application.bucket-name")
    private String bucketName;
}
