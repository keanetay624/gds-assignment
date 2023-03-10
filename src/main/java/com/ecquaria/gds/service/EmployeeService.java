package com.ecquaria.gds.service;

import com.ecquaria.gds.exception.InvalidIdException;
import com.ecquaria.gds.exception.InvalidSalaryFormatException;
import com.ecquaria.gds.exception.LoginAlreadyExistsException;
import com.ecquaria.gds.exception.MissingRequiredFieldsException;
import com.ecquaria.gds.model.Employee;
import com.ecquaria.gds.repository.EmployeeRepository;
import com.ecquaria.gds.util.ValidatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee getEmployeeById(String id) {
        return employeeRepository.findEmployeesById(id);
    }

    public Employee addEmployee(String id, String name, String login, String salary) throws InvalidIdException, InvalidSalaryFormatException {
        ValidatorUtil.checkSalaryFormat(salary);
        Employee newEmployee = new Employee(id, name, login, new BigDecimal(salary));

        if (employeeRepository.existsById(id)) {
            throw new InvalidIdException("ID" + id + " already exists!");
        }


        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    public Employee deleteEmployee(String id) throws InvalidIdException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee e = null;

        if (id.isEmpty()) {
            throw new InvalidIdException("ID cannot be empty!");
        }

        if (!employeeRepository.existsById(id)) {
            throw new InvalidIdException("ID" + id + " does not exist!");
        }

        if (optionalEmployee.isPresent()) {
             e = optionalEmployee.get();
        }

        employeeRepository.deleteById(id);
        return e;
    }

    public Employee updateEmployee(String id, String name, String login, String salary) throws InvalidIdException, InvalidSalaryFormatException,
            MissingRequiredFieldsException, LoginAlreadyExistsException {
        ValidatorUtil.checkSalaryFormat(salary);
        Employee newEmployee = new Employee(id, name, login, new BigDecimal(salary));

        if (!employeeRepository.existsById(id)) {
            throw new InvalidIdException("ID" + id + " does not exist!");
        }

        if (id.isEmpty() || name.isEmpty() || login.isEmpty() || salary.isEmpty()) {
            throw new MissingRequiredFieldsException("Missing required fields.");
        }

        Employee possibleLoginConflictEmployee = employeeRepository.findEmployeeByLogin(login);
        Employee targetEmployeeForUpdate =  employeeRepository.findEmployeesById(id);

        if (possibleLoginConflictEmployee != null && !possibleLoginConflictEmployee.equals(targetEmployeeForUpdate)) {
            throw new LoginAlreadyExistsException("Employee with target login already exists!");
        }

        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    public Page<Employee> getEmployeesByParams(String minSalary, String maxSalary,
                                               String limit, String offset,String sort) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit), Sort.by(getSortDirection(sort),sort.substring(1)));

        Page<Employee> pageEmployee;
        double minSal = Double.parseDouble(minSalary);
        double maxSal = Double.parseDouble(maxSalary);
        pageEmployee = employeeRepository.findEmployeesByCustomSalaryBetween(paging, minSal, maxSal);

        return pageEmployee;
    }

    public Sort.Direction getSortDirection(String sort) {
        if (sort.charAt(0) == '+') return Sort.Direction.ASC;
        return Sort.Direction.DESC;
    }
}
