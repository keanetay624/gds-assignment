package com.ecquaria.gds.service;

import com.ecquaria.gds.exception.InvalidIdException;
import com.ecquaria.gds.model.Employee;
import com.ecquaria.gds.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final MongoTemplate mongoTemplate;

    public Employee getEmployeeById(String id) {
        return employeeRepository.findEmployeesById(id);
    }

    public Employee addEmployee(String id, String name, String login, String salary) throws InvalidIdException {
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

    public Employee updateEmployee(String id, String name, String login, String salary) throws InvalidIdException {
        Employee newEmployee = new Employee(id, name, login, new BigDecimal(salary));

        if (!employeeRepository.existsById(id)) {
            throw new InvalidIdException("ID" + id + " does not exist!");
        }
        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    public List<Employee> getEmployeesByParams(String minSalary, String maxSalary,
                                               String limit, String offset,String sort) {
        final Pageable pageableRequest = PageRequest.of(0, Integer.parseInt(limit));
        Query query = new Query();
        BigDecimal bd1 = new BigDecimal(minSalary);
        BigDecimal bd2 = new BigDecimal(maxSalary);
        query.addCriteria(Criteria.where("salary").gte(bd1.doubleValue()).lte(bd2.doubleValue()));
        query.limit(Integer.parseInt(limit));

        List<String> validatedSortValues = isValidSort(sort);

        if (!validatedSortValues.isEmpty()) {
            if (validatedSortValues.get(0).equals("ASC")) {
                query.with(Sort.by(Sort.Direction.ASC, validatedSortValues.get(1)));
            } else {
                query.with(Sort.by(Sort.Direction.DESC, validatedSortValues.get(1)));
            }
            query.with(pageableRequest);
        }

        if (!offset.isEmpty()) {
            query.skip(Integer.parseInt(offset));
        }
        return mongoTemplate.find(query, Employee.class);

    }

    public List<String> isValidSort(String sortValue) {
        List<String> strList = new ArrayList<>();
        if (sortValue.charAt(0) == '+') {
            strList.add("ASC");
        } else if (sortValue.charAt(0) == '-') {
            strList.add("DESC");
        } else {
            return strList;
        }

        strList.add(sortValue.substring(1));
        return strList;
    }
}
