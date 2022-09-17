package com.example.ce_musicplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

import java.io.IOException;

public class Controlador_biblio1 {

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

}
