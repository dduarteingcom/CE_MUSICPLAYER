package com.example.ce_musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

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

        //b1.GuardarBiblio();
        //b1.eliminarBiblio();
        //Biblioteca biblioteca2 = new Biblioteca("prueba");
        //biblioteca2.prueba(3);
        //b1.EditarNombreBiblio("Punk");
        Bibliotecas BMauricio=new Bibliotecas();
        Bibliotecas BDaniel=new Bibliotecas();
        Bibliotecas BMbappe=new Bibliotecas();


       // BMauricio.LectorBM();



    }

    public void cambioEscena(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setScene(new Scene(pane));
    }


    public static void arduino(){
        SerialPort port = new SerialPort("COM3");
        try {
            port.openPort();
            port.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            port.addEventListener((SerialPortEvent event)->{
                if(event.isRXCHAR()){
                    try {
                        if (port.readString().equals("1")){
                            System.out.println("Hola");
                        }
                    } catch (SerialPortException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {



        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Mauricio", "mauluna52@gmail.com", "Cartago", "Valeria26"));
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Daniel", "dduarte@gmail.com", "San Jose", "Dduarte55"));
        launch();
    }
}