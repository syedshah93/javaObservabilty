package com.mcds.observabilty.services.impl;

import com.mcds.observabilty.utils.S3Utils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import software.amazon.awssdk.services.s3.model.S3Exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class ObservabilityServiceImplTest {

    @InjectMocks
    private ObservabilityServiceImpl observabilityService;

    @Mock
    private S3Utils s3Utils;

    public ObservabilityServiceImplTest() {
        AutoCloseable autoCloseable = openMocks(this);
    }

    @Test
    void getS3FilesSuccess() {
        String fileName = "test-file.txt";
        observabilityService.getS3Files(fileName);
        verify(s3Utils).downloadFile(fileName);
    }

    @Test
    void getS3FileError() {
        String fileName = "test-file.txt";
        doThrow(S3Exception.class).when(s3Utils).downloadFile(fileName);
        assertThrows(S3Exception.class, () -> observabilityService.getS3Files(fileName));
    }
}