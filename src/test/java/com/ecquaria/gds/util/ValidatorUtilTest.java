package com.ecquaria.gds.util;

import com.ecquaria.gds.exception.InvalidSalaryFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorUtilTest {

    @Test
    @DisplayName("checkSalaryFormat should return throw invalidSalaryException if salary < 0")
    void testCheckSalaryFormat() {
        assertThrows(InvalidSalaryFormatException.class,() -> ValidatorUtil.checkSalaryFormat("-2"));
    }

    @Test
    @DisplayName("checkSalaryFormat should return throw invalidSalaryException if not double format")
    void testCheckSalaryFormat2() {
        assertThrows(InvalidSalaryFormatException.class,() -> ValidatorUtil.checkSalaryFormat("k9"));
    }

    @Test
    @DisplayName("checkSalaryFormat should return throw invalidSalaryException if min sal > max sal")
    void testCheckSalaryFormat3() {
        assertThrows(InvalidSalaryFormatException.class,() -> ValidatorUtil.checkSalaryFormat("2","1"));
    }

    @Test
    @DisplayName("checkSalaryDecimalPlaces should return throw invalidSalaryException if sal > 2 decimal places")
    void testCheckSalaryDecimalPlaces() {
        assertThrows(InvalidSalaryFormatException.class,() -> ValidatorUtil.checkSalaryDecimalPlaces("1.023"));
    }

}