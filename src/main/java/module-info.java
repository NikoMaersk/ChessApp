module com.example.chessmobile {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chessmobile to javafx.fxml;
    exports com.example.chessmobile;
    exports controller;
    opens controller to javafx.fxml;
}