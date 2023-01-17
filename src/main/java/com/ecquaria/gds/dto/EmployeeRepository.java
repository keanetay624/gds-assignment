package com.ecquaria.gds.dto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query("{name: '?0'}")
    Employee findEmployeeByEmployeeName(String name);

    public long count();

}
