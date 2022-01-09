package validation;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsDateValidTest {

    @Test
    public void isDateValidRequires2021dash2dash2AndRequiresStudentReturnsTrue(){
      Date date = Date.valueOf(LocalDate.of(2021,2,2));
      Validator validator = new Validator();
      boolean result = validator.isDateValid(date, "Student");
      assertEquals(true, result);
    }

    @Test
    public void isDateValidRequires2022dash2dash2AndRequiresEnrollmentReturnsTrue(){
        Date date = Date.valueOf(LocalDate.of(2022,2,2));
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Enrollment");
        assertEquals(true, result);
    }

    @Test
    public void isDateValidRequires2021dash2dash2AndRequiresEnrollmentReturnsFalse(){
        Date date = Date.valueOf(LocalDate.of(2021,2,2));
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Enrollment");
        assertEquals(false, result);
    }

    @Test
    public void isDateValidRequires2022dash2dash2AndRequiresStudentReturnsFalse(){
        Date date = Date.valueOf(LocalDate.of(2022,2,2));
        Validator validator = new Validator();
        boolean result = validator.isDateValid(date, "Student");
        assertEquals(false, result);
    }



}
