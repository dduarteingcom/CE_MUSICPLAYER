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

/**
 * Controlador_inicio contiene los atributos y métodos necesarios para el funcionamiento de la ventanda de inicio de sesion.
 */
public class Controlador_inicio extends Lista_usuarios implements Initializable {
    /**
     * Boton utilizado para iniciar sesion
     */
    @FXML
    private Button boton_iniciar;
    /**
     * Entrada de texto utilizado para escribir el correo del usuario
     */
    @FXML
    private TextField correo_entry;
    /**
     * Label para indicar en cuál entrada de texto se escribe el correo
     */
    @FXML
    private Label correo_label;
    @FXML
    private TextField nom_entry;
    /**
     * Label para indicar en cuál entrada de texto se escribe el nombre
     */
    @FXML
    private Label nom_label;
    /**
     * Entrada utilizada para escribir la contraseña del usuario.
     */
    @FXML
    private PasswordField pass_entry;
    /**
     * Label utilizado para indicar en cuál entrada escribir.
     */
    @FXML
    private Label pass_label, error_inicio, prov_label;
    /**
     * Cuadro de seleccion que contiene las provincias a elegir
     */
    @FXML
    private ChoiceBox<String> prov_lista;
    /**
     * Instancia global de GestionDatos que contiene la informacion de todos los usuarios
     */
    public static GestionDatos CurrentLista = new GestionDatos();
    /**
     * Variable global que indicará cuál usuario inició sesión
     */
    public static String UsuarioSelec;
    /**
     * Atributo tipo array que contiene los nombres de las provincias
     */
    private String[] provincias = {"San Jose", "Cartago", "Alajuela", "Puntarenas", "Heredia", "Guanacaste"};

    /**
     * Método que permite inicializar la ventana y agrega el array de las provincias al cuadro de seleccion @param url @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prov_lista.getItems().addAll(provincias);
    }

    /**
     * Funcion que al presionar el boton iniciar, recoja los datos escritos del usuario para indicar cuál usuario está iniciando sesión y así leer su respectivo archivo XML. @param event @throws IOException
     */
    public void boton_iniciar(ActionEvent event) throws IOException {
        Main m = new Main();
        Usuario usuario = new Usuario(nom_entry.getText(), correo_entry.getText(), prov_lista.getValue(), pass_entry.getText());
        Nodo_usuarios actual = Lista_usuarios.lista_usuarios.head;
        boolean encontrado = false;
        while (actual != null)
            if (actual.getData().getNombre().equals(usuario.getNombre()) & actual.getData().getCorreo().equals(usuario.getCorreo()) & actual.getData().getProvincia().equals(usuario.getProvincia()) & actual.getData().getContrasena().equals(usuario.getContrasena())) {
                if (usuario.getNombre().equals("Mauricio")) {
                    GestionDatos user = new GestionDatos();
                    user.LectorBM("Mauricio");
                    CurrentLista = user;
                    UsuarioSelec = "Mauricio";
                }
                if (usuario.getNombre().equals("Daniel")) {
                    GestionDatos user = new GestionDatos();
                    user.LectorBM("Daniel");
                    CurrentLista = user;
                    UsuarioSelec = "Daniel";
                }
                if (usuario.getNombre().equals("Mbappe")) {
                    GestionDatos user = new GestionDatos();
                    user.LectorBM("Mbappe");
                    CurrentLista = user;
                    UsuarioSelec = "Mbappe";
                }
                encontrado = true;
                System.out.println("Usuario encontrado");
                m.cambioEscena("Ventana_biblioteca1.fxml");
                break;
            } else actual = actual.next;
        if (!encontrado) error_inicio.setText("Usuario No Encontrado");
    }
}
