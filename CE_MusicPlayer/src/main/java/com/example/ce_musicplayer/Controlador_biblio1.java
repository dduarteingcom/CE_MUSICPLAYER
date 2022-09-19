package com.example.ce_musicplayer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

import java.io.File;
import java.io.IOException;
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
    void nextCancion() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(songs.get(songNumber).getName());
                }
            });
            mediaPlayer.play();

        } else {
            songNumber = 0;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(songs.get(songNumber).getName());
                }
            });

            mediaPlayer.play();
        }
    }
    @FXML
    void pausar() {
        mediaPlayer.pause();

    }

    void volumen(int volumen){
        mediaPlayer.setVolume(volumen * 0.01);
    }

    @FXML
    void prevCancion() {
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(songs.get(songNumber).getName());
                }
            });
            mediaPlayer.play();

        } else {
            songNumber = songs.size() - 1;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(songs.get(songNumber).getName());
                }
            });
            mediaPlayer.play();
        }
    }
    @FXML
    void reproducir() {
        mediaPlayer.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        obtenerCanciones();
        arduino();
    }


    public void arduino() {
        SerialPort port = new SerialPort("COM3");
        try {
            port.openPort();
            port.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            port.addEventListener((SerialPortEvent event) -> {
                if (event.isRXCHAR()) {
                    try {
                        String msg = port.readString();
                        if (msg.equals("1")) {
                            reproducir();
                        }
                        if (msg.equals("2")) {
                            pausar();
                        }
                        if (msg.equals("3")) {
                            nextCancion();
                        }
                        if (msg.equals("4")) {
                            prevCancion();
                        }
                        if (msg.equals("5")) {
                            volumen(0);
                        }
                        if (msg.equals("6")) {
                            volumen(20);
                        }
                        if (msg.equals("7")) {
                            volumen(40);
                        }
                        if (msg.equals("8")) {
                            volumen(60);
                        }
                        if (msg.equals("9")) {
                            volumen(80);
                        }
                        if (msg.equals("+")) {
                            volumen(100);
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

    public void obtenerCanciones() {
        songs = new ArrayList<File>();

        directory = new File("CE_MusicPlayer/Canciones");

        files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                songs.add(file);
                System.out.println(file);
            }
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());

    }


}
