package validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class isEmailValidTest {

    @Test
    public void testIsEmailAddressValidWithTestmailAtMailDotComReturnsTrue() {
        String emailAddress = "Testmail@mail.com";
        Validator validator = new Validator();
        boolean result = validator.isEmailAddressValid(emailAddress);
        assertEquals(true, result);
    }

    @Test
    public void testIsEmailAddressValidWithTAtMailDotComReturnsTrue() {
        String emailAddress = "T@mail.com";
        Validator validator = new Validator();
        boolean result = validator.isEmailAddressValid(emailAddress);
        assertEquals(true, result);
    }

    @Test
    public void testIsEmailAddressValidWithAtMailDotComReturnsFalse() {
        String emailAddress = "@mail.com";
        Validator validator = new Validator();
        boolean result = validator.isEmailAddressValid(emailAddress);
        assertEquals(false, result);
    }

    @Test
    public void testIsEmailAddressValidWithMailDotComReturnsFalse() {
        String emailAddress = "mail.com";
        Validator validator = new Validator();
        boolean result = validator.isEmailAddressValid(emailAddress);
        assertEquals(false, result);
    }

    @Test
    public void testIsEmailAddressValidWithAtDotComReturnsFalse(){
        String emailAddress = "@.com";
        Validator validator = new Validator();
        boolean result = validator.isEmailAddressValid(emailAddress);
        assertEquals(false, result);
    }
    @Test
    public void testIsEmailAddressValidWithTestDotCom(){
        String emailAddress = "test.com";
        Validator validator = new Validator();
        boolean result = validator.isEmailAddressValid(emailAddress);
        assertEquals(false, result);
    }
}
