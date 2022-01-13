package validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IsDateValidTest {

    @Test
    public void isDateValidRequires2021dash2dash2AndRequiresStudentReturnsTrue() {
        String date = "2021-02-02";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(true, result);
    }

    @Test
    public void isDateValidRequires2022dash2dash2AndRequiresEnrollmentReturnsTrue() {
        String date = "2022-02-02";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Enrollment");
        assertEquals(true, result);
    }

    @Test
    public void isDateValidRequires2021dash2dash2AndRequiresEnrollmentReturnsFalse() {
        String date = "2021-2-2";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Enrollment");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2022dash2dash2AndRequiresStudentReturnsFalse() {
        String date = "2022-2-2";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2022dash13dash2AndRequiresStudentReturnsFalse() {
        String date = "2022-13-13";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2024dash2dash29AndRequiresStudentReturnsTrue() {
        String date = "2024-02-29";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Enrollment");
        assertEquals(true, result);
    }

    @Test
    public void isDateValidRequires2020dash2dash29AndRequiresStudentReturnsTrue() {
        String date = "2020-02-29";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(true, result);
    }

    @Test
    public void isDateValidRequires2019dash2dash29AndRequiresStudentReturnsFalse() {
        String date = "2019-2-29";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2021dash2dash29AndRequiresStudentReturnsFalse() {
        String date = "2021-2-29";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2022dashminus1dash1RequiresStudentReturnsFalse() {
        String date = "2022--1-29";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2022dashminus1dashminus1RequiresStudentReturnsFalse() {
        String date = "2022--1--1";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequiresminus2022dashminus1dashminus1RequiresStudentReturnsFalse() {
        String date = "-2022--1--1";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2022dash1dashminus1RequiresStudentReturnsFalse() {
        String date = "2022-1--1";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequiresminus2022dash1dashminus1RequiresStudentReturnsFalse() {
        String date = "-2022-1--1";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequiresminus2022dash1dash1RequiresStudentReturnsFalse() {
        String date = "-2022-1-1";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequiresminus2022dashminus1dash1RequiresStudentReturnsFalse() {
        String date = "-2022--1-1";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2022dash1dash32RequiresStudentReturnsFalse() {
        String date = "2022-1-32";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2022dash13dash1RequiresStudentReturnsFalse() {
        String date = "2022-13-1";
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }

}
