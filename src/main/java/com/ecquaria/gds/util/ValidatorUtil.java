package com.ecquaria.gds.util;

import com.ecquaria.gds.exception.InvalidSalaryFormatException;

public class ValidatorUtil {
    public static void checkSalaryFormat(String salaryString) throws InvalidSalaryFormatException {
        try {
            double salary = Double.parseDouble(salaryString);
            checkSalaryDecimalPlaces(salaryString);

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

    public static void checkSalaryDecimalPlaces(String toEvaluate) throws InvalidSalaryFormatException {
        String[] strArr = toEvaluate.split("\\.");

        if (strArr.length == 2) {
            if (strArr[1].length() > 2) {
                throw new InvalidSalaryFormatException("Salary cannot have more than 2 decimal places");
            }
        }
    }
}
