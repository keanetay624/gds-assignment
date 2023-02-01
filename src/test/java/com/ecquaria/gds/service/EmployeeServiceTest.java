package com.ecquaria.gds.service;

import com.ecquaria.gds.exception.InvalidIdException;
import com.ecquaria.gds.exception.InvalidSalaryFormatException;
import com.ecquaria.gds.model.Employee;
import com.ecquaria.gds.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@DataMongoTest
class EmployeeServiceTest {
    @Mock private EmployeeRepository employeeRepository;
    private AutoCloseable autoCloseable;
    private EmployeeService underTest;

    @BeforeEach
    void init() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new EmployeeService(employeeRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testAddEmployee() throws InvalidSalaryFormatException, InvalidIdException {
        Employee expectedEmployee = new Employee("uniqueId", "uniqueName","uniqueLogin",new BigDecimal("3000.00"));
        underTest.addEmployee("uniqueId", "uniqueName","uniqueLogin","3000.00");
        verify(employeeRepository).save(expectedEmployee);
        verify(employeeRepository).existsById(expectedEmployee.getId());
    }

    @Test
    void testDeleteEmployee() throws InvalidIdException {
        Employee expectedEmployee = new Employee("uniqueId", "uniqueName","uniqueLogin",new BigDecimal("3000.00"));
        Optional<Employee> oEmployee = Optional.of(expectedEmployee);
        when(employeeRepository.findById(expectedEmployee.getId())).thenReturn(oEmployee);
        when(employeeRepository.existsById(expectedEmployee.getId())).thenReturn(true);
        underTest.deleteEmployee(expectedEmployee.getId());
        verify(employeeRepository, times(1)).deleteById(expectedEmployee.getId());
    }

    @Test
    void testUpdateEmployee() throws InvalidSalaryFormatException, InvalidIdException {
        Employee expectedEmployee = new Employee("uniqueId", "uniqueName","uniqueLogin",new BigDecimal("3000.00"));
        when(employeeRepository.existsById(expectedEmployee.getId())).thenReturn(true);
        underTest.updateEmployee("uniqueId", "uniqueName","uniqueLogin","3000.00");
        verify(employeeRepository, times(1)).save(expectedEmployee);
    }

    @Test
    void testGetEmployeesByParams() {
        String offset = "0";
        String limit = "30";
        Double minSal = 0.0;
        Double maxSal = 50000.0;
        String sort = "+id";
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        underTest.getEmployeesByParams(String.valueOf(minSal), String.valueOf(maxSal),limit,offset, sort);
        verify(employeeRepository, times(1)).findEmployeesByCustomSalaryBetween(paging, minSal, maxSal);
    }
}