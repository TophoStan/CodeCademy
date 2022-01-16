package validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class isPercentageValidTest {

/**
 * @subcontract is valid {
 *      * @requires 0 <= percentage && percentage <= 100;
 *      * @ensures \result = true
 *      }
 * */
    @Test
    public void testIsPercentageValidRequires0ReturnsTrue(){
        int input = 0;
        Validator validator = new Validator();
        boolean result = validator.isPercentageValid(input);
        assertEquals(true, result);
    }
    /**
     * @subcontract is valid {
     *      * @requires 0 <= percentage && percentage <= 100;
     *      * @ensures \result = true
     *      }
     * */
    @Test
    public void testIsPercentageValidRequires100ReturnsTrue(){
        int input = 100;
        Validator validator = new Validator();
        boolean result = validator.isPercentageValid(input);
        assertEquals(true, result);
    }
    /**
     * @subcontract is valid {
     *      * @requires 0 <= percentage && percentage <= 100;
     *      * @ensures \result = true
     *      }
     * */
    @Test
    public void testIsPercentageValidRequires50ReturnsTrue(){
        int input = 50;
        Validator validator = new Validator();
        boolean result = validator.isPercentageValid(input);
        assertEquals(true, result);
    }
    /**
     * @subcontract is not valid {
     *      * @requires 0 > percentage && percentage > 100;
     *      * @ensures \result = false
     *     }
     * */

    @Test
    public void testIsPercentageValidRequiresMinus1ReturnsFalse(){
        int input = -1;
        Validator validator = new Validator();
        boolean result = validator.isPercentageValid(input);
        assertEquals(false, result);
    }
    /**
     * @subcontract is not valid {
     *     * @requires 0 > percentage && percentage > 100;
     *     * @ensures \result = false
     *      }
     * */
    @Test
    public void testIsPercentageValidRequires101ReturnsFalse(){
        int input = 101;
        Validator validator = new Validator();
        boolean result = validator.isPercentageValid(input);
        assertEquals(false, result);
    }
}
