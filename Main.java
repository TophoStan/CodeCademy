import domain.*;
import repository.*;

public class Main {
    public static void main(String[] args) {
        // database connection
        DatabaseConnection database = new DatabaseConnection();

        database.connect();
        database.viewTable();
    }
}
