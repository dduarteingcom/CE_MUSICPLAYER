package com.example.ce_musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main contiene los métodos que permite iniciar la aplicación
 */
public class Main extends Application {
    /**
     * Atributo global que permite cambiar entre escenas.
     */
    private static Stage stg;

    /**
     * Método que carga el archivo fxml y lo inicia
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Ventana_sesion.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Método que permite cambiar de escenas entre archivos fxml.
     * @param fxml
     * @throws IOException
     */
    public void cambioEscena(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setScene(new Scene(pane));
        stg.show();
    }

    /**
     * Método que inicia la aplicación.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Mauricio", "mauluna52@gmail.com", "Cartago", "Valeria26"));
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Daniel", "dduarte@gmail.com", "San Jose", "Dduarte55"));
        Lista_usuarios.lista_usuarios.insertarUsuario(new Usuario("Mbappe", "mfrappe@gmail.com", "Alajuela", "Mfrappe88"));
        launch();
    }
}