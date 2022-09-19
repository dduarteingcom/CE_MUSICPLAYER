package com.example.ce_musicplayer;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private static Stage stg;
    static SerialPort serial_Port;

    @Override
    public void start(Stage stage) throws Exception {


        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Ventana_biblioteca1.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

        Biblioteca b1 = new Biblioteca("Amapolas");
        Cancion cancion1 = new Cancion("a", "b", "c", "d", "e", "f", null, null, "g");
        Cancion cancion2 = new Cancion("h", "i", "j", "k", "l", "m", null, null, "n");
        Cancion cancion3 = new Cancion("o", "p", "q", "r", "s", "t", null, null, "u");
        b1.InsertarCan(cancion1);
        b1.InsertarCan(cancion2);
        b1.InsertarCan(cancion3);
        Biblioteca b2 = new Biblioteca("Amapolas2");
        Cancion cancion4 = new Cancion("a", "b", "c", "d", "e", "f", null, null, "g");
        Cancion cancion5 = new Cancion("h", "i", "j", "k", "l", "m", null, null, "n");
        Cancion cancion6 = new Cancion("o", "p", "q", "r", "s", "t", null, null, "u");
        b2.InsertarCan(cancion4);
        b2.InsertarCan(cancion5);
        b2.InsertarCan(cancion6);
        UserDaniel prueba=new UserDaniel("Daniel","daduarte@estudiantec.cr","San José","yoyo");
        prueba.bibliotecas.insertBiblio(b1);
        prueba.bibliotecas.insertBiblio(b2);
        prueba.LectorDM();
        System.out.println(prueba.bibliotecas.Primero.getNombre());
        System.out.println(prueba.bibliotecas.Primero.Sig.getNombre());






    }

    public void cambioEscena(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setScene(new Scene(pane));
    }


    public static void main(String[] args) throws IOException {

        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Mauricio", "mauluna52@gmail.com", "Cartago", "Valeria26"));
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Daniel", "dduarte@gmail.com", "San Jose", "Dduarte55"));
        launch();

    }




}