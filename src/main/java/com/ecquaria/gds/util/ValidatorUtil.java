package com.ecquaria.gds.util;

import com.ecquaria.gds.exception.InvalidSalaryFormatException;

public class ValidatorUtil {
    public static void checkSalaryFormat(String salaryString) throws InvalidSalaryFormatException {
        try {
            Double salary = Double.parseDouble(salaryString);
            if (salary < 0) {
                throw new InvalidSalaryFormatException("Salary cannot be less than zero.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidSalaryFormatException("Salary must follow double format");
        }
    }
}
