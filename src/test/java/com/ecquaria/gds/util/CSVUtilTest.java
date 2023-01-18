package com.ecquaria.gds.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

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
}