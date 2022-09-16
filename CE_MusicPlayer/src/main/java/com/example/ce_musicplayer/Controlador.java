package com.example.ce_musicplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    private String[] provincias = {"San Jose","Cartago","Alajuela","Puntarenas",
    "Heredia","Guanacaste"};
    //private Media media;
    //private MediaPlayer mediaPlayer;

    private File directory;
    private File[] files;



    private int songNumber;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};

    private Timer timer;
    private TimerTask task;

    private boolean running;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prov_lista.getItems().addAll(provincias);

    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void boton_iniciar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ventana_biblioteca.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

}
