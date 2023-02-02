package com.ecquaria.gds.service;

import com.ecquaria.gds.exception.ColumnMismatchException;
import com.ecquaria.gds.exception.DuplicateLoginOrIDException;
import com.ecquaria.gds.exception.InvalidSalaryFormatException;
import com.ecquaria.gds.model.Employee;
import com.ecquaria.gds.repository.EmployeeRepository;
import com.ecquaria.gds.util.CSVUtil;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class CSVService {
    private final EmployeeRepository repository;

    public void save(MultipartFile file) throws ColumnMismatchException, DuplicateLoginOrIDException, DuplicateKeyException, InvalidSalaryFormatException {
        try {
            List<Employee> employees = CSVUtil.csvToEmployee(file.getInputStream());
            repository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
}
