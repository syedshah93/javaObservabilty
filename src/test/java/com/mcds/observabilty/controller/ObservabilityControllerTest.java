package com.mcds.observabilty.controller;

import com.mcds.observabilty.exception.CustomException;
import com.mcds.observabilty.services.ObservabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObservabilityController.class)
class ObservabilityControllerTest {


    @MockBean
    private ObservabilityService observabilityService;

    @InjectMocks
    private ObservabilityController observabilityController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Autowired
    MockMvc mockMvc;

    @Test
    void getS3Objects() throws Exception {

        String fileName = "testfile.txt";

        this.mockMvc.perform(get("/private/v1/getfile").contentType(MediaType.APPLICATION_JSON).param("fileName",fileName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully downloaded the file"));
        verify(observabilityService).getS3Files(fileName);
    }

    @Test
    void getS3ObjectsError() throws Exception {

        String fileName = "testfile.txt";
        doThrow(new CustomException("Something went Wrong !!","Sorry")).when(observabilityService).getS3Files(fileName);
        this.mockMvc.perform(get("/private/v1/getfile").contentType(MediaType.APPLICATION_JSON).param("fileName",fileName))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(observabilityService).getS3Files(fileName);
    }
}