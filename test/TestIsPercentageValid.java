package test;
import org.junit.Test;

import validation.InputValidation;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestIsPercentageValid {
    @Test
    public void testIsPercentageValidRequires0ReturnsTrue(){
        int input = 0;
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isPercentageValid(input);
        assertEquals(true, result);
    }
    @Test
    public void testIsPercentageValidRequires100ReturnsTrue(){
        int input = 100;
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isPercentageValid(input);
        assertEquals(true, result);
    }
    @Test
    public void testIsPercentageValidRequires50ReturnsTrue(){
        int input = 50;
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isPercentageValid(input);
        assertEquals(true, result);
    }
    @Test
    public void testIsPercentageValidRequiresMinus1ReturnsFalse(){
        int input = -1;
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isPercentageValid(input);
        assertEquals(false, result);
    }
    @Test
    public void testIsPercentageValidRequires101ReturnsFalse(){
        int input = 101;
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isPercentageValid(input);
        assertEquals(false, result);
    }


}
