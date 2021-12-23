module com.example.codecademy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.codecademy to javafx.fxml;
    exports com.example.codecademy;
}