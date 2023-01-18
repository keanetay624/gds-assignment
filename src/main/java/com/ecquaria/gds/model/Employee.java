package com.ecquaria.gds.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data // for lombok
@Document // for use with collections. Mongodb
public class Employee {
    @Id
    private String employeeId;
    @Indexed(unique = true)
    private String login;
    private String employeeName;
    private BigDecimal salary;

    public Employee(String login, String employeeName, BigDecimal salary) {
        this.login = login;
        this.employeeName = employeeName;
        this.salary = salary;
    }
}
