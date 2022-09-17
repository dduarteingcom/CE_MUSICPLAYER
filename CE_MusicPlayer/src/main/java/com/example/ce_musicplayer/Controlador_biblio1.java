package com.example.ce_musicplayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    private File directory;
    private File[] archivos;

    private ArrayList<File> canciones;

    private Timer timer;
    private TimerTask task;
    private boolean running;

    private Media media;
    private MediaPlayer mediaPlayer;

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

    }

    @FXML
    void pausar(ActionEvent event) {

    }

    @FXML
    void prevCancion(ActionEvent event) {

    }

    @FXML
    void reproducir(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hola");
        canciones = new ArrayList<File>();
        directory = new File("CE_MusicPlayer/src/main/Canciones");
        archivos = directory.listFiles();
        if (archivos != null){
            for(File file : archivos){
                canciones.add(file);
                System.out.println(file);
            }
        }
    }
}
