module com.example.filosofos {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.filosofos to javafx.fxml;
    exports com.example.filosofos;
}