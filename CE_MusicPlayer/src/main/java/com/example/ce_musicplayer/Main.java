package com.example.ce_musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Ventana_sesion.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        Biblioteca b1 = new Biblioteca("A");
        Cancion cancion1 = new Cancion("a", "b", "c", "d", "e", "f", null, null, "g");
        Cancion cancion2 = new Cancion("h", "i", "j", "k", "l", "m", null, null, "n");
        Cancion cancion3 = new Cancion("o", "p", "q", "r", "s", "t", null, null, "u");
        b1.ingresar(cancion1);
        b1.ingresar(cancion2);
        b1.ingresar(cancion3);
        b1.Guardar();
    }

    public void cambioEscena(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        // Usuario mauro = new Usuario("Mauricio", "mauluna52@gmail.com", "Cartago", "Valeria26");
        // Usuario daniel = new Usuario("Daniel", "dduarte@gmail.com", "San Jose", "Dduarte55");


        //Lista_usuarios lista_usuarios = new Lista_usuarios();
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Mauricio", "mauluna52@gmail.com", "Cartago", "Valeria26"));
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Daniel", "dduarte@gmail.com", "San Jose", "Dduarte55"));

        launch();
    }
}