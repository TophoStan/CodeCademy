import domain.*;
import repository.*;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();

        connection.connect();
    }
}
