package com.ecquaria.gds.service;

import com.ecquaria.gds.exception.ColumnMismatchException;
import com.ecquaria.gds.exception.DuplicateLoginOrIDException;
import com.ecquaria.gds.exception.InvalidSalaryFormatException;
import com.ecquaria.gds.repository.EmployeeRepository;
import com.ecquaria.gds.util.CSVUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.mockito.Mockito.verify;
@DataMongoTest
class CSVServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    private AutoCloseable autoCloseable;
    private CSVService underTest;

    @BeforeEach
    void init() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CSVService(employeeRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    @Test
    void testSave() throws InvalidSalaryFormatException, ColumnMismatchException, DuplicateLoginOrIDException, FileNotFoundException {
        MockMultipartFile mockFile = new MockMultipartFile("data", "filename.txt", "text/csv", "some xml".getBytes());
        File testCsvFile = new File("C:\\Users\\ECQ984\\Desktop\\GDS Challenge\\src\\main\\resources\\testComment1.csv");
        InputStream is = new FileInputStream(testCsvFile);

        try (MockedStatic<CSVUtil> mockedStatic = Mockito.mockStatic(CSVUtil.class)) {
            mockedStatic.when(() -> CSVUtil.csvToEmployee(is)
            ).thenReturn(new ArrayList<>());
        }
        underTest.save(mockFile);
        verify(employeeRepository).saveAll(new ArrayList<>());
    }
}