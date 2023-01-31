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

    public static void checkSalaryFormat(String minSalaryString, String maxSalaryString) throws InvalidSalaryFormatException {
        double minSal = Double.parseDouble(minSalaryString);
        double maxSal = Double.parseDouble(maxSalaryString);

        if (maxSal < minSal) {
            throw new InvalidSalaryFormatException("Max salary cannot be less than min salary.");
        }
    }
}
