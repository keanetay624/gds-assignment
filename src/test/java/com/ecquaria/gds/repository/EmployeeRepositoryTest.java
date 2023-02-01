package com.ecquaria.gds.repository;

import com.ecquaria.gds.model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository underTest;

    static Employee e;

    @BeforeAll
    static void init() {
        e = new Employee("uniqueid", "uniquename", "uniquelogin", new BigDecimal(9000));
    }

    @Test
    @DisplayName("Repository save should save object to db. DeleteById should remove object with id.")
    void testSaveAndDeleteById() {
        underTest.save(e);
        Employee actual = underTest.findEmployeesById("uniqueid");
        assertEquals(e, actual);
        underTest.deleteById("uniqueid");
        Employee deleted = underTest.findEmployeesById("uniqueid");
        assertNull(deleted);
    }


}