package com.ecquaria.gds;

import com.ecquaria.gds.dto.Employee;
import com.ecquaria.gds.dto.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;

@SpringBootApplication
@EnableMongoRepositories
public class GdsApplication implements CommandLineRunner {

	@Autowired
	EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(GdsApplication.class, args);
	}


	void createGroceryItems() {
		System.out.println("Data creation started...");
		employeeRepository.save(new Employee("hi", "keane", BigDecimal.TEN));
		System.out.println("Data creation complete...");
	}

	@Override
	public void run(String... args) throws Exception {
		createGroceryItems();
	}
}
