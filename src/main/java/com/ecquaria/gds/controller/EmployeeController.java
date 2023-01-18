package com.ecquaria.gds.controller;

import com.ecquaria.gds.exception.ColumnMismatchException;
import com.ecquaria.gds.exception.DuplicateLoginOrIDException;
import com.ecquaria.gds.model.Employee;
import com.ecquaria.gds.model.ResponseMessage;
import com.ecquaria.gds.service.CSVService;
import com.ecquaria.gds.service.EmployeeService;
import com.ecquaria.gds.util.CSVUtil;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CSVService csvService;
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        String error = "";

        if (CSVUtil.hasCSVFormat(file)) {
            try {
                csvService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (ColumnMismatchException | DuplicateLoginOrIDException e) {
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

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
