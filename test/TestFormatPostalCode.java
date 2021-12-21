package test;

import org.junit.Test;

import validation.InputValidation;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFormatPostalCode {

    @Test
    public void testFormatPostalCodeRequires1234ABReturns1234SpaceAB() {
        String input = "1234aB";
        InputValidation inputValidation = new InputValidation();
        String result = inputValidation.formatPostalCode(input);
        assertEquals("1234 AB", result);
    }
    @Test
    public void testFormatPostalCodeRequires12345ABReturnsInvalidSize(){
        String input = "12345aB";
        InputValidation inputValidation = new InputValidation();
        String result = inputValidation.formatPostalCode(input);
        assertEquals("invalid size", result);
    }
    @Test 
    public void testFormatPostalCodeRequires123ABReturnsInvalidSize(){
        String input = "123aB";
        InputValidation inputValidation = new InputValidation();
        String result = inputValidation.formatPostalCode(input);
        assertEquals("invalid size", result);
    }
    @Test 
    public void testFormatPostalCodeRequires0123ABReturnsInvalidSize(){
        String input = "0123aB";
        InputValidation inputValidation = new InputValidation();
        String result = inputValidation.formatPostalCode(input);
        assertEquals("cant start with 0", result);
    }

}
