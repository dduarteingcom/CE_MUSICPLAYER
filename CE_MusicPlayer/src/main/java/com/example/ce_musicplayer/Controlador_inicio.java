package com.example.ce_musicplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_inicio extends Lista_usuarios implements Initializable {

    @FXML
    private Button boton_iniciar;

    @FXML
    private TextField correo_entry;

    @FXML
    private Label correo_label;

    @FXML
    private TextField biblioEntry;

    @FXML
    private Button insertButton;

    @FXML
    private Label errorLabel;

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
    public static GestionDatos CurrentLista = new GestionDatos();
    public static String UsuarioSelec;
    private String[] provincias = {"San Jose", "Cartago", "Alajuela", "Puntarenas",
            "Heredia", "Guanacaste"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prov_lista.getItems().addAll(provincias);
    }

    private Stage stage;
    private Scene scene;


    public void boton_iniciar(ActionEvent event) throws IOException {
        Main m = new Main();
        Usuario usuario = new Usuario(nom_entry.getText(), correo_entry.getText(), prov_lista.getValue(), pass_entry.getText());
        Nodo_usuarios actual = Lista_usuarios.lista_usuarios.head;

        boolean encontrado = false;
        while (actual != null) {
            if (actual.getData().getNombre().equals(usuario.getNombre()) & actual.getData().getCorreo().equals(usuario.getCorreo()) &
                    actual.getData().getProvincia().equals(usuario.getProvincia()) & actual.getData().getContrasena().equals(usuario.getContrasena())) {

                if (usuario.getNombre().equals("Mauricio")){
                    GestionDatos user = new GestionDatos();
                    user.Lector("Mauricio");
                    CurrentLista =user;
                    UsuarioSelec="Mauricio";


                }
                if (usuario.getNombre().equals("Daniel")){
                    GestionDatos user = new GestionDatos();
                    user.Lector("Daniel");
                    CurrentLista =user;
                    UsuarioSelec="Daniel";
                }
                if (usuario.getNombre().equals("Mbappe")){
                    GestionDatos user = new GestionDatos();
                    user.Lector("Mbappe");
                    CurrentLista =user;
                    UsuarioSelec="Mbappe";
                }
                encontrado = true;
                System.out.println("Usuario encontrado");

                m.cambioEscena("Ventana_biblioteca1.fxml");
                break;
            } else {
                actual = actual.next;
            }
        }
        if (!encontrado) {
            error_inicio.setText("Usuario No Encontrado");
        }

    }




}
