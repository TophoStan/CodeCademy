import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import domain.*;
import repository.*;

public class Main {
    public static void main(String[] args) {
        // database connection
        DatabaseConnection database = new DatabaseConnection();

        database.connect();
        // SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        // int year = 2014;
        // int month = 10;
        // int day = 31;
        // Calendar cal = Calendar.getInstance();
        // cal.set(Calendar.YEAR, year);
        // cal.set(Calendar.MONTH, month - 1); // <-- months start
        // // at 0.
        // cal.set(Calendar.DAY_OF_MONTH, day);

        // java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        // System.out.println(sdf.format(date));

        
        try {
            ArrayList<Student> students = database.retrieveStudents();
            System.out.println(students.toString());
            database.deleteStudentFromDatabase(students.get(0));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
