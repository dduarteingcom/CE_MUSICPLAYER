package com.example.ce_musicplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_inicio extends Lista_usuarios implements Initializable{

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
    private PasswordField pass_entry;
    @FXML
    private Label pass_label;

    @FXML
    private Label error_inicio;

    @FXML
    private Label prov_label;

    @FXML
    private ChoiceBox<String> prov_lista;
    private String[] provincias = {"San Jose","Cartago","Alajuela","Puntarenas",
    "Heredia","Guanacaste"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prov_lista.getItems().addAll(provincias);

    }
    private Stage stage;
    private Scene scene;


    public void boton_iniciar(ActionEvent event) throws IOException {

        Usuario usuario = new Usuario(nom_entry.getText(), correo_entry.getText(), prov_lista.getValue(), pass_entry.getText());
        Nodo_usuarios actual = Lista_usuarios.lista_usuarios.head;
        boolean encontrado = false;
        while (actual != null){
            if (actual.getData().getNombre().equals(usuario.getNombre()) & actual.getData().getCorreo().equals(usuario.getCorreo()) &
            actual.getData().getProvincia().equals(usuario.getProvincia()) & actual.getData().getContrasena().equals(usuario.getContrasena())){
                encontrado = true;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ventana_biblioteca1.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
            else {
                actual = actual.next;
            }
        }
        if (encontrado == false){
            error_inicio.setText("Usuario No Encontrado");
        }

    }




}