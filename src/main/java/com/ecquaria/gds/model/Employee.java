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
@Data
@Document
public class Employee {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String login;
    private BigDecimal salary;

    public Employee(String login, String name, BigDecimal salary) {
        this.login = login;
        this.name = name;
        this.salary = salary;
    }
}
