package com.mcds.observabilty.services.impl;

import com.mcds.observabilty.services.ObservabilityService;
import com.mcds.observabilty.utils.S3Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObservabilityServiceImpl implements ObservabilityService {

    @Autowired
    private S3Utils s3Utils;

    private final static Logger logger = LogManager.getLogger(ObservabilityServiceImpl.class);

    @Override
    public void getS3Files(String fileName) {
        logger.info("Called getS3Files(), with filename {}",fileName);
        s3Utils.downloadFile(fileName);
    }
}
