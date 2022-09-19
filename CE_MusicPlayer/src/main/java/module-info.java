module com.example.ce_musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires javafx.media;
    requires com.fazecast.jSerialComm;

    requires java.logging;

    opens com.example.ce_musicplayer to javafx.fxml;
    exports com.example.ce_musicplayer;
}