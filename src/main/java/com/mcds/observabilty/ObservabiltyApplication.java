package com.mcds.observabilty;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class ObservabiltyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObservabiltyApplication.class, args);
    }

}
