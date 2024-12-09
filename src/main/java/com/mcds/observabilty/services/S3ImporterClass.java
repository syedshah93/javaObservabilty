//package com.mcds.observabilty.services;
//
//import com.mcds.observabilty.config.AWSConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//@Service
//public class S3ImporterClass {
//
//    @Autowired
//    AWSConfig awsConfig;
//
//
//    public void downloadFile(String fileName) {
//        System.out.format("Downloading %s from S3 bucket %s...\n", awsConfig.getRegion(), awsConfig.getBucketName());
//        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(awsConfig.getRegion()).build();
//        try {
//            S3Object o = s3.getObject(awsConfig.getBucketName(), fileName);
//            S3ObjectInputStream s3is = o.getObjectContent();
//            FileOutputStream fos = new FileOutputStream(new File(fileName));
//            byte[] read_buf = new byte[1024];
//            int read_len = 0;
//            while ((read_len = s3is.read(read_buf)) > 0) {
//                fos.write(read_buf, 0, read_len);
//            }
//            s3is.close();
//            fos.close();
//        } catch (AmazonServiceException e) {
//            System.err.println(e.getErrorMessage());
//            System.exit(1);
//        } catch (FileNotFoundException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//    }
//}
