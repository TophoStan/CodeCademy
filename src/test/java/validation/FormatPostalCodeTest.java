package validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatPostalCodeTest {

    @Test
    public void testFormatPostalCodeRequires1234ABReturns1234SpaceAB() {
        String input = "1234aB";
        Validator validator = new Validator();
        String result = validator.formatPostalCode(input);
        assertEquals("1234 AB", result);
    }

    @Test
    public void testFormatPostalCodeRequires12345ABReturnsNull() {
        String input = "12345aB";
        Validator validator = new Validator();
        String result = validator.formatPostalCode(input);
        assertEquals(null, result);
    }

    @Test
    public void testFormatPostalCodeRequires123ABReturnsNull() {
        String input = "123aB";
        Validator validator = new Validator();
        String result = validator.formatPostalCode(input);
        assertEquals(null, result);
    }

    @Test
    public void testFormatPostalCodeRequires0123ABReturnsNull() {
        String input = "0123aB";
        Validator validator = new Validator();
        String result = validator.formatPostalCode(input);
        assertEquals(null, result);
    }


}