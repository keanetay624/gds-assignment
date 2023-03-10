package com.ecquaria.gds.repository;

import com.ecquaria.gds.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query("{_id: '?0'}")
    Employee findEmployeesById(String id);

    @Query("{login: '?0'}")
    Employee findEmployeeByLogin(String login);

    @Query(value = "{'salary':{ $gte: ?0, $lte: ?1}}")
    Page<Employee> findEmployeesByCustomSalaryBetween(Pageable pageable, Double minSal, Double maxSal);
    long count();

}
