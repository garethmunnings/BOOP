module com.example.boop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    //requires javafx.media;

    opens com.example.boop to javafx.fxml;
    exports com.example.boop;
}