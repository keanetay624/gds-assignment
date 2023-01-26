package com.ecquaria.gds.repository;

import com.ecquaria.gds.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query("{_id: '?0'}")
    Employee findEmployeesById(String id);

    long count();

}
