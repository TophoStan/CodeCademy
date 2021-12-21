package test;

import org.junit.Test;

import validation.InputValidation;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIsURLValid {

    @Test
    public void testIsURLValidRequiresHTTPS2DotsDoubleSlashWWWDotGoogleDotComReturnsTrue(){
        String url = "https://www.google.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isURLValid(url);
        assertEquals(true, result);
    }
    @Test
    public void testIsURLValidRequiresHTTP2DotsDoubleSlashWWWDotGoogleDotComReturnsTrue(){
        String url = "http://www.google.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isURLValid(url);
        assertEquals(true, result);
    }
    @Test 
    public void testIsURLValidRequiresHTTP2DotsDoubleSlashDotComReturnsFalse(){
        String url = "http://.com";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isURLValid(url);
        assertEquals(false, result);
    }
    @Test
    public void testIsURLValidRequiresHTTP2DotsDoubleSlashGoogleReturnsFalse(){
        String url = "http://google";
        InputValidation inputValidation = new InputValidation();
        boolean result = inputValidation.isURLValid(url);
        assertEquals(false, result);
    }

    
}
