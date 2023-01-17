package com.ecquaria.gds.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data // for lombok
@Document // for use with collections. Mongodb
public class Employee {
    @Id
    private String employeeId;
    private String login;
    private String employeeName;
    private BigDecimal salary;

    public Employee(String login, String employeeName, BigDecimal salary) {
        this.login = login;
        this.employeeName = employeeName;
        this.salary = salary;
    }
}
