package validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class IsURLValidTest {
    @Test
    public void testIsURLValidRequiresHTTPS2DotsDoubleSlashWWWDotGoogleDotComReturnsTrue(){
        String url = "https://www.google.com";
        Validator validator = new Validator();
        boolean result = validator.isURLValid(url);
        assertEquals(true, result);
    }
    @Test
    public void testIsURLValidRequiresHTTP2DotsDoubleSlashWWWDotGoogleDotComReturnsTrue(){
        String url = "http://www.google.com";
        Validator validator = new Validator();
        boolean result = validator.isURLValid(url);
        assertEquals(true, result);
    }
    @Test
    public void testIsURLValidRequiresHTTP2DotsDoubleSlashDotComReturnsFalse(){
        String url = "http://.com";
        Validator validator = new Validator();
        boolean result = validator.isURLValid(url);
        assertEquals(false, result);
    }
    @Test
    public void testIsURLValidRequiresHTTP2DotsDoubleSlashGoogleReturnsFalse(){
        String url = "http://google";
        Validator validator = new Validator();
        boolean result = validator.isURLValid(url);
        assertEquals(false, result);
    }
}
