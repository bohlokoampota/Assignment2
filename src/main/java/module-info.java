module com.example.ltriviagame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ltriviagame to javafx.fxml;
    exports com.example.ltriviagame;
}