package com.example.ce_musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private static Stage stg;


    @Override
    public void start(Stage stage) throws Exception {


        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Ventana_sesion.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

        ListaMayor list = new ListaMayor();
        list.LectorBM("Mauricio");
        System.out.println(list.listabibliotecas.Primero.getNombre());
        System.out.println(list.listabibliotecas.Primero.Primero.getNombre());
        System.out.println(list.listabibliotecas.Primero.Primero.Sig.getNombre());
        System.out.println(list.listabibliotecas.Primero.Primero.Sig.Sig.getNombre());
        System.out.println(list.listabibliotecas.Primero.Primero.Sig.Sig.Sig.getNombre());
        System.out.println(list.listabibliotecas.Primero.Sig.getNombre());
        System.out.println(list.listabibliotecas.Primero.Sig.Primero.getNombre());
        System.out.println(list.listabibliotecas.Primero.Sig.Primero.Sig.getNombre());
        System.out.println(list.listabibliotecas.Primero.Sig.Primero.Sig.Sig.getNombre());
        System.out.println(list.listabibliotecas.Primero.Sig.Primero.Sig.Sig.Sig.getNombre());
    }

    public void cambioEscena(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setScene(new Scene(pane));
        stg.show();
    }


    public static void main(String[] args) throws IOException {


        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Mauricio", "mauluna52@gmail.com", "Cartago", "Valeria26"));
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Daniel", "dduarte@gmail.com", "San Jose", "Dduarte55"));
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Mbappe", "mfrappe@gmail.com", "Alajuela", "Mfrappe88"));

        launch();


    }
}