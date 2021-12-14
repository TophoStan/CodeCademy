import domain.*;
import repository.*;

public class Main {
    public static void main(String[] args) {
        // database connection
        DatabaseConnection connection = new DatabaseConnection();

        connection.connect();
    }
}
