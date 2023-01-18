package com.ecquaria.gds.util;

import com.ecquaria.gds.constants.ApplicationConstants;
import com.ecquaria.gds.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    public static List<Employee> csvToEmployee(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Employee> employees = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                if (isComment(csvRecord)) continue;
                if (!isFourColumnsFilled(csvRecord)) continue;
                Employee employee = new Employee(
                        csvRecord.get(0),
                        csvRecord.get(1),
                        csvRecord.get(2),
                        new BigDecimal(csvRecord.get(3))
                );

                if (isUniqueLoginAndID(employee, employees)) {
                    employees.add(employee);
                }
            }

            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        return ApplicationConstants.MIME_TYPE_CSV.equals(file.getContentType());
    }

    public static boolean isComment(CSVRecord csvRecord) {
        return !csvRecord.get(0).isEmpty() && csvRecord.get(0).charAt(0) == ApplicationConstants.HASH;
    }

    public static boolean isFourColumnsFilled(CSVRecord csvRecord) {
        return !csvRecord.get(0).isEmpty() && !csvRecord.get(1).isEmpty() && !csvRecord.get(2).isEmpty() && !csvRecord.get(3).isEmpty();
    }

    public static boolean isUniqueLoginAndID(Employee employee, List<Employee> employees) {
        for (Employee e : employees) {
            if (e.getEmployeeId().equals(employee.getEmployeeId()) || e.getLogin().equals(employee.getLogin())) return false;
        }
        return true;
    }

}