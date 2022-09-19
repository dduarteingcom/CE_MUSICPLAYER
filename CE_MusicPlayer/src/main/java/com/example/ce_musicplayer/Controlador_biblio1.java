package com.example.ce_musicplayer;
import com.fazecast.jSerialComm.SerialPort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controlador_biblio1 implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private ProgressBar barraCancion;

    @FXML
    private MenuBar barra_menu;

    @FXML
    private MenuItem cerrar_sesion;

    @FXML
    private Button modoButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button playButton;

    @FXML
    private Button prevButton;

    @FXML
    private Label songLabel;

    @FXML
    private Slider volumen;

    private Media media;
    private MediaPlayer mediaPlayer;

    private File directory;
    private File[] files;

    private ArrayList<File> songs;

    private Timer timer;
    private TimerTask task;
    private boolean running;
    private int songNumber;


    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        Main m = new Main();
        m.cambioEscena("Ventana_sesion.fxml");
    }
    @FXML
    void modoContinuo(ActionEvent event) {

    }

    @FXML
    void nextCancion(ActionEvent event) {
        if (songNumber < songs.size() - 1){
            songNumber ++;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            reproducir();

        }
        else {
            songNumber = 0;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());

            reproducir();


        }

    }

    @FXML
    void pausar(ActionEvent event) {
        mediaPlayer.pause();

    }

    @FXML
    void prevCancion(ActionEvent event) {
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            reproducir();

        } else {
            songNumber = songs.size() -1;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            reproducir();
        }
    }

    @FXML



    void reproducir() {

        mediaPlayer.play();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       // prueba_arduino();

        songs = new ArrayList<File>();

        directory = new File("C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\Canciones");




        files = directory.listFiles();

        if (files != null){
            for(File file : files){
                songs.add(file);
                System.out.println(file);
            }
        }


        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);




        songLabel.setText(songs.get(songNumber).getName());

    }
    static SerialPort serial_Port;

    public static void prueba()throws IOException {

        SerialPort[] get_port = SerialPort.getCommPorts();
        for(SerialPort port : get_port){

            System.out.println(port.getSystemPortName());
            serial_Port = SerialPort.getCommPort(port.getSystemPortName());

            serial_Port.openPort();
            serial_Port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        }
        serial_Port.setBaudRate(9600);
        InputStream inputStream = serial_Port.getInputStream();

        while (true){
            char msg = (char) inputStream.read();
            System.out.println(msg);
        }
    }


}
