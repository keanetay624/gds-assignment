package com.ecquaria.gds.util;

import com.ecquaria.gds.exception.InvalidSalaryFormatException;
import com.ecquaria.gds.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVUtilTest {

    @Test
    @DisplayName("isComment should return false if first char of csvRecord is empty")
    public void isCommentTestOne() throws IOException {
        File testCsvFile = new File("C:\\Users\\ECQ984\\Desktop\\GDS Challenge\\src\\main\\resources\\testComment1.csv");
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
        File testCsvFile = new File("C:\\Users\\ECQ984\\Desktop\\GDS Challenge\\src\\main\\resources\\testComment2.csv");
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
        File testCsvFile = new File("C:\\Users\\ECQ984\\Desktop\\GDS Challenge\\src\\main\\resources\\testComment3.csv");
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
        File testCsvFile = new File("C:\\Users\\ECQ984\\Desktop\\GDS Challenge\\src\\main\\resources\\testTooFewColumns.csv");
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
        File testCsvFile = new File("C:\\Users\\ECQ984\\Desktop\\GDS Challenge\\src\\main\\resources\\test - control.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertFalse(CSVUtil.isLessThanFourColumns(csvRecord));
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
        File testCsvFile = new File("C:\\Users\\ECQ984\\Desktop\\GDS Challenge\\src\\main\\resources\\testInvalidSalaryLessThanZero.csv");
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
        File testCsvFile = new File("C:\\Users\\ECQ984\\Desktop\\GDS Challenge\\src\\main\\resources\\testInvalidSalary.csv");
        InputStream is = new FileInputStream(testCsvFile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            assertThrows(InvalidSalaryFormatException.class,() -> CSVUtil.checkSalaryFormat(csvRecord));
        }
    }

}