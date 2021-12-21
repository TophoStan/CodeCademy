package test;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import validation.InputValidation;

public class TestInputValidation {

    @Test
    public void testIsEmailAddressValidWithTestmailAtMailDotComReturnsTrue() {
        String emailAddress = "Testmail@mail.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isEmailAddressValid(emailAddress);
        assertEquals(true, result);
    }

    @Test
    public void testIsEmailAddressValidWithTAtMailDotComReturnsTrue() {
        String emailAddress = "T@mail.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isEmailAddressValid(emailAddress);
        assertEquals(true, result);
    }

    @Test
    public void testIsEmailAddressValidWithAtMailDotComReturnsFalse() {
        String emailAddress = "@mail.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isEmailAddressValid(emailAddress);
        assertEquals(false, result);
    }

    @Test
    public void testIsEmailAddressValidWithMailDotComReturnsFalse() {
        String emailAddress = "mail.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isEmailAddressValid(emailAddress);
        assertEquals(false, result);
    }

    @Test 
    public void testIsEmailAddressValidWithAtDotComReturnsFalse(){
        String emailAddress = "@.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isEmailAddressValid(emailAddress);
        assertEquals(false, result);
    }
    @Test 
    public void testIsEmailAddressValidWithTestDotCom(){
        String emailAddress = "test.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isEmailAddressValid(emailAddress);
        assertEquals(false, result);
    }

}
