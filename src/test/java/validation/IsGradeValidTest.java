package validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class IsGradeValidTest {

    @Test
    public void isGradeValidRequires1ReturnsTrue(){
        int grade = 1;
        Validator validator = new Validator();
        boolean result = validator.isGradeValid(grade);
        assertEquals(true, result);
    }

    @Test
    public void isGradeValidRequires10ReturnsTrue(){
        int grade = 10;
        Validator validator = new Validator();
        boolean result = validator.isGradeValid(grade);
        assertEquals(true, result);
    }

    @Test
    public void isGradeValidRequires5ReturnsTrue(){
        int grade = 5;
        Validator validator = new Validator();
        boolean result = validator.isGradeValid(grade);
        assertEquals(true, result);
    }

    @Test
    public void isGradeValidRequiresMinus1ReturnsFalse(){
        int grade = -1;
        Validator validator = new Validator();
        boolean result = validator.isGradeValid(grade);
        assertEquals(false, result);
    }
    @Test
    public void isGradeValidRequires11ReturnsFalse(){
        int grade = 11;
        Validator validator = new Validator();
        boolean result = validator.isGradeValid(grade);
        assertEquals(false, result);
    }

}
