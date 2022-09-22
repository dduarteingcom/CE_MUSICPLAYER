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

        Biblioteca b1= new Biblioteca("");
        b1.InsertnewSong("Bones");
        System.out.println(b1.Primero.getGen());



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

        //Cancion cancion1= new Cancion("a","a","a","a","a","a",null,null,"a");
        //Cancion cancion2= new Cancion("a","a","a","a","a","a",null,null,"a");
        //Cancion cancion3= new Cancion("a","a","a","a","a","a",null,null,"a");
        Biblioteca b1= new Biblioteca("Mfrappe");
        b1.setFechaC("Hoy");
        b1.InsertnewSong("Livin On A Prayer");
        System.out.println(b1.Primero.getNombre());
        System.out.println(b1.Primero.getGen());
        System.out.println(b1.Primero.getAlbum());
        /*
        b1.InsertarCan(cancion2);
        b1.InsertarCan(cancion3);
        ListaMayor listaMayor = new ListaMayor();
        listaMayor.listabibliotecas.insertBiblio(b1);
        listaMayor.Guardar("Daniel");
        listaMayor.LectorBM("Daniel");
        System.out.println(listaMayor.listabibliotecas.Primero.getFechaC());

         */


        launch();


    }
}