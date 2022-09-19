module com.example.ce_musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires javafx.media;
    requires java.logging;
    requires java.desktop;
    requires jssc;


    opens com.example.ce_musicplayer to javafx.fxml;
    exports com.example.ce_musicplayer;
}