package test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Test;

import domain.Student;
import repository.DatabaseConnection;

/**
 * TestAddStudentToDatabase
 */
public class TestAddStudentToDatabase {

    /**
     * @subcontract student is null {
     * @requires student != null;
     * @throws (NullPointerException) Student != null
     *                                }
     */
    @Test
    public void testAddStudentToDatabaseRequiresNullSignalsNullPointerException() {
        try {
            DatabaseConnection dbc = new DatabaseConnection();
            dbc.connect();
            Student test = null;
            boolean result = dbc.addStudentToDatabase(test);
            assertEquals(false, result);
        } catch (Exception e) {
           System.out.println(e);
        }

    }

    @Test
    public void testAddStudentToDatabaseRequiresNullSignalsSQLException() {
        try {
            DatabaseConnection dbc = new DatabaseConnection();
            Date date = new Date(0);
            Student test = new Student("emailAddress", "name", "gender", date, "street", 5, "postalCode", "city",
                    "country");
            boolean result = dbc.addStudentToDatabase(test);
            assertEquals(false, result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}