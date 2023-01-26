package com.ecquaria.gds.controller;

import com.ecquaria.gds.exception.ColumnMismatchException;
import com.ecquaria.gds.exception.DuplicateLoginOrIDException;
import com.ecquaria.gds.exception.InvalidIdException;
import com.ecquaria.gds.exception.InvalidSalaryFormatException;
import com.ecquaria.gds.model.Employee;
import com.ecquaria.gds.model.ResponseMessage;
import com.ecquaria.gds.service.CSVService;
import com.ecquaria.gds.service.EmployeeService;
import com.ecquaria.gds.util.CSVUtil;
import com.ecquaria.gds.util.ValidatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("users")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CSVService csvService;
    @GetMapping
    public ResponseEntity<ResponseMessage> getEmployeesByParams(
            @RequestParam(name = "minSalary") String minSalary,
            @RequestParam(name = "maxSalary") String maxSalary,
            @RequestParam(name = "limit") String limit,
            @RequestParam(name = "offset") String offset,
            @RequestParam(name = "sort") String sort) {
        String message;
        String error;

        try {
            ValidatorUtil.checkSalaryFormat(minSalary);
            ValidatorUtil.checkSalaryFormat(maxSalary);

            List<Employee> list = employeeService.getEmployeesByParams(minSalary, maxSalary, limit, offset, sort);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("","", list));
        } catch (InvalidSalaryFormatException e) {
            message = "Invalid Salary Format";
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,error));
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseMessage> getEmployeeById(@PathVariable String id) {
        String message;
        String error;

        try {
            Employee e = employeeService.getEmployeeById(id);
            List<Employee> list = new ArrayList<>();
            list.add(e);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("","", list));
        } catch (Exception e) {
            message = "Failed to get employee with id: " + e;
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,error));
        }

    }

    @PostMapping("{id}")
    public ResponseEntity<ResponseMessage> addEmployee(@PathVariable String id,
                                                       @RequestParam(name = "name") String name,
                                                       @RequestParam(name = "login") String login,
                                                       @RequestParam(name = "salary") String salary) {
        String message;
        String error;

        try {
            Employee e = employeeService.addEmployee(id, name, login, salary);
            List<Employee> list = new ArrayList<>();
            list.add(e);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("","", list));
        } catch (InvalidIdException e) {
            message = "Failed to create employee with id: " + e;
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, error));
        } catch (Exception e) {
            message = "Failed to create employee with id: " + e;
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,error));
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMessage> deleteEmployee(@PathVariable String id) {
        String message;
        String error;

        try {
            Employee e = employeeService.deleteEmployee(id);
            List<Employee> list = new ArrayList<>();
            list.add(e);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("","", list));
        } catch (InvalidIdException e) {
            message = "Failed to delete employee with id: " + e;
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, error));
        } catch (Exception e) {
            message = "Failed to delete employee with id: " + e;
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,error));
        }

    }

    @PatchMapping("{id}")
    public ResponseEntity<ResponseMessage> updateEmployee(@PathVariable String id,
                                                          @RequestParam(name = "name") String name,
                                                          @RequestParam(name = "login") String login,
                                                          @RequestParam(name = "salary") String salary) {
        String message;
        String error;

        try {
            Employee e = employeeService.updateEmployee(id, name, login, salary);
            List<Employee> list = new ArrayList<>();
            list.add(e);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("","", list));
        } catch (InvalidIdException e) {
            message = "Failed to update employee with id: " + e;
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, error));
        } catch (Exception e) {
            message = "Failed to update employee with id: " + e;
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,error));
        }

    }

    @PostMapping("upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;
        String error;

        if (file.isEmpty()) {
            message = "File: " + file.getOriginalFilename() + " is empty!";
            error = "Empty files are not permitted";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, error));
        }

        if (!CSVUtil.hasCSVFormat(file)) {
            message = "Please upload a csv file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }

        try {
            csvService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (ColumnMismatchException | DuplicateLoginOrIDException | InvalidSalaryFormatException e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            error = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, error));
        } catch (DuplicateKeyException e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            error = "Duplicate key of record in database";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, error));
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
