module com.example.ce_musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.example.ce_musicplayer to javafx.fxml;
    exports com.example.ce_musicplayer;
}