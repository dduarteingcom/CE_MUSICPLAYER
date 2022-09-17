package com.example.ce_musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ventana_sesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

        Biblioteca b1 = new Biblioteca("Amapolas");
        Cancion cancion1 = new Cancion("a", "b", "c", "d", "e", "f", null, null, "g");
        Cancion cancion2 = new Cancion("h", "i", "j", "k", "l", "m", null, null, "n");
        Cancion cancion3 = new Cancion("o", "p", "q", "r", "s", "t", null, null, "u");
        b1.InsertarCan(cancion1);
        b1.InsertarCan(cancion2);
        b1.InsertarCan(cancion3);

        //b1.GuardarBiblio();
        b1.eliminarBiblio();


    }

    public static void main(String[] args) {
        launch();
    }
}