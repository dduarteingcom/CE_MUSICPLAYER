package com.example.ce_musicplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador implements Initializable{

    @FXML
    private Button boton_iniciar;

    @FXML
    private TextField correo_entry;

    @FXML
    private Label correo_label;

    @FXML
    private TextField nom_entry;

    @FXML
    private Label nom_label;

    @FXML
    private TextField pass_entry;

    @FXML
    private Label pass_label;

    @FXML
    private Label prov_label;

    @FXML
    private ChoiceBox<String> prov_lista;
    private String[] provincias = {"San Jose","Car","Alajuela","Puntarenas",
    "Heredia","Guanacaste"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prov_lista.getItems().addAll(provincias);

    }
}
