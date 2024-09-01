package com.mcds.observabilty.controller;


import com.mcds.observabilty.services.ObservabilityService;
import com.mcds.observabilty.services.impl.ObservabilityServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/v1")
public class ObservabilityController {

    @Autowired
    private ObservabilityService observabilityService;


    private final static Logger logger = LogManager.getLogger(ObservabilityServiceImpl.class);

    @GetMapping("/getfile")
    public ResponseEntity<String> getS3Objects(@RequestParam("fileName") String fileName){

            logger.info("/private/v1/getfile endpoint called with fileName = " + fileName );
            observabilityService.getS3Files(fileName);
        return new ResponseEntity<String>("Successfully downloaded the file", HttpStatus.OK);
    }
}
