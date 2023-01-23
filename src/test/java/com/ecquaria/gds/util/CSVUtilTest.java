package com.ecquaria.gds.util;

import com.ecquaria.gds.exception.InvalidSalaryFormatException;
import com.ecquaria.gds.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVUtilTest {

    @Test
    @DisplayName("hasCSVFormat should return true if multipartFile has csv extension")
    public void hasCSVFormatTestOne() throws IOException {

        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return "text/csv";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        assertTrue(CSVUtil.hasCSVFormat(file));
    }

    @Test
    @DisplayName("hasCSVFormat should return false if multipartFile does not have csv extension")
    public void hasCSVFormatTestTwo() throws IOException {

        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return "text/json";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        assertFalse(CSVUtil.hasCSVFormat(file));
    }

    @Test
    @DisplayName("isComment should return false if first char of csvRecord is empty")
    public void isCommentTestOne() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\testComment1.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertFalse(CSVUtil.isComment(csvRecord));
        }
    }

    @Test
    @DisplayName("isComment should return false if first char of csvRecord is not '#'")
    public void isCommentTestTwo() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\testComment2.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertFalse(CSVUtil.isComment(csvRecord));
        }
    }

    @Test
    @DisplayName("isComment should return true if first char of csvRecord is '#'")
    public void isCommentTestThree() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\testComment3.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertTrue(CSVUtil.isComment(csvRecord));
        }
    }

    @Test
    @DisplayName("isLessThanFourColumns should return true if any of first 4 csvRecord values are empty")
    public void isLessThanFourColumnsTestOne() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\testTooFewColumns.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertTrue(CSVUtil.isLessThanFourColumns(csvRecord));
        }
    }

    @Test
    @DisplayName("isLessThanFourColumns should return false all first 4 columns of csvRecord are not empty")
    public void isLessThanFourColumnsTestTwo() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\test - control.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertFalse(CSVUtil.isLessThanFourColumns(csvRecord));
        }
    }

    @Test
    @DisplayName("isMoreThanFourColumns should return true if csvRecord has a size value > 4")
    public void isMoreThanFourColumnsTestOne() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\testTooManyColumns.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertTrue(CSVUtil.isMoreThanFourColumns(csvRecord));
        }
    }

    @Test
    @DisplayName("isMoreThanFourColumns should return false if csvRecord has a size value = 4")
    public void isMoreThanFourColumnsTestTwo() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\test - control.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertFalse(CSVUtil.isMoreThanFourColumns(csvRecord));
        }
    }

    @Test
    @DisplayName("isUniqueLoginAndID should return false all if login is equal")
    public void isUniqueLoginAndIDTestOne() {
        Employee e1 = new Employee("1", "test","test", new BigDecimal("3"));
        Employee e2 = new Employee("2", "test","test", new BigDecimal("3"));
        List<Employee> employees = new ArrayList<>();
        employees.add(e1);
        employees.add(e2);
        assertFalse(CSVUtil.isUniqueLoginAndID(e1, employees));
    }

    @Test
    @DisplayName("isUniqueLoginAndID should return false if id is equal")
    public void isUniqueLoginAndIDTestTwo() {
        Employee e1 = new Employee("1", "test","test", new BigDecimal("3"));
        Employee e2 = new Employee("1", "testTwo","test", new BigDecimal("3"));
        List<Employee> employees = new ArrayList<>();
        employees.add(e1);
        assertFalse(CSVUtil.isUniqueLoginAndID(e2, employees));
    }

    @Test
    @DisplayName("isUniqueLoginAndID should return true if id not equal and login not equal")
    public void isUniqueLoginAndIDTestThree() {
        Employee e1 = new Employee("1", "test","test", new BigDecimal("3"));
        Employee e2 = new Employee("2", "testTwo","test", new BigDecimal("3"));
        List<Employee> employees = new ArrayList<>();
        employees.add(e1);
        assertTrue(CSVUtil.isUniqueLoginAndID(e2, employees));
    }

    @Test
    @DisplayName("checkSalaryFormat should return throw invalidSalaryException if salary < 0")
    public void checkSalaryFormatTestOne() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\testInvalidSalaryLessThanZero.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertThrows(InvalidSalaryFormatException.class,() -> CSVUtil.checkSalaryFormat(csvRecord));
        }
    }

    @Test
    @DisplayName("checkSalaryFormat should return throw invalidSalaryException if salary < 0")
    public void checkSalaryFormatTestTwo() throws IOException {
        File testCsvFile = new File("G:\\gds test csv\\testInvalidSalary.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertThrows(InvalidSalaryFormatException.class,() -> CSVUtil.checkSalaryFormat(csvRecord));
        }
    }

}