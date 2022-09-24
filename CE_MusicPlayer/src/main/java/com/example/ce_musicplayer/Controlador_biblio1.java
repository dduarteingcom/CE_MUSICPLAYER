package com.example.ce_musicplayer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.ce_musicplayer.Controlador_inicio.*;

/**
 * Clase que contiene los atributos y necesarios para funcionar la ventana del reproductor de música.
 */
public class Controlador_biblio1 implements Initializable {
    /**
     * barraCancion es una pequeña barra que muestra el progreso de la cancion que se está reproduciendo
     */
    @FXML
    private ProgressBar barraCancion;

    /**
     * barra_menu va a guardar los diferentes opciones de menú para diferentes funciones
     */
    @FXML
    private MenuBar barra_menu;

    /**
     * Opcion que permita al usuario realizar la funcion de cerrar sesión.
     */
    @FXML
    private MenuItem cerrar_sesion;
    /**
     * Lista que muestra las canciones de una biblioteca
     */
    @FXML
    private ListView<String> Lista_canciones;
    /**
     * Botón que permita al usuario activar el modo continuo
     */
    @FXML
    private Button modoButton;
    /**
     * Botón que permite al usuario pasar a la siguiente cancion
     */
    @FXML
    private Button nextButton;
    /**
     * Botón que permite al usuario pausar la cancion que se está reproduciendo.
     */

    @FXML
    private Button pauseButton;
    /**
     * Botón que permite al usuario reproducir la cancion.
     */

    @FXML
    private Button playButton;
    /**
     * Botón que permite al usuario pasar a la cancion anterior.
     */

    @FXML
    private Button prevButton;
    /**
     * Label que muestra el nombre de la canción que se está reproduciendo.
     */

    @FXML
    private Label songLabel;

    @FXML
    private Menu menuCancion;

    @FXML
    private Menu menuCuenta;

    @FXML
    private Menu menuSelec;
    @FXML
    private Button BotCrearBi;
    @FXML
    private Button editBiblioButton;

    @FXML
    private TextField nuevoNombre;

    @FXML
    private TextField EntryNomBi;
    @FXML
    private TitledPane VenCBiblioteca;
    @FXML
    private Button BBohemian;

    @FXML
    private Button BBones;
    @FXML
    private Button BEnemy;

    @FXML
    private Button BEye;

    @FXML
    private Button BLivin;

    @FXML
    private Button BNeverita;

    @FXML
    private Button BNothing;

    @FXML
    private Button BStolen;

    @FXML
    private Button BTacones;

    @FXML
    private Button BTom;
    @FXML
    private MenuItem LBohemian;

    @FXML
    private MenuItem LBones;

    @FXML
    private MenuItem LEnemy;
    @FXML
    private MenuItem LEye;
    @FXML
    private MenuItem LLivin;

    @FXML
    private MenuItem LNeverita;

    @FXML
    private MenuItem LNothing;

    @FXML
    private MenuItem LStolen;

    @FXML
    private MenuItem LTacones;

    @FXML
    private MenuItem LTom;

    @FXML
    private Label label_usuario;
    @FXML
    private TextField album_editar;

    @FXML
    private TextField artista_editar;

    @FXML
    private TextField año_editar;

    @FXML
    private ChoiceBox<String> cancion_a_editar;

    @FXML
    private Button editarButton;

    @FXML
    private TextField genero_editar;

    @FXML
    private Button borrar_button;

    @FXML
    private ChoiceBox<String> cancion_a_borrar;

    @FXML
    private ChoiceBox<String> biblio_a_borrar;
    @FXML
    private Button borrar_biblioButton;
    @FXML
    private TextField letra_editar;


    private Media media;
    private MediaPlayer mediaPlayer;

    private Timer timer;
    private TimerTask task;
    private boolean running;


    private Biblioteca biblio_seleccionada;
    private Cancion cancion_actual;

    private int x = 0;

    private boolean modo_activado = false;
    private File file;
    private ObservableList<String> list = FXCollections.observableArrayList();



    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException, SerialPortException {
        Main m = new Main();
        port.closePort();
        m.cambioEscena("Ventana_sesion.fxml");
    }

    @FXML
    private void modoContinuo() {
        if (modo_activado){
            modo_activado = false;
            System.out.println("Modo continuo desactivado");
        }
        else {
            modo_activado = true;
            System.out.println("Modo continuo activado");
        }

    }

    @FXML
    private void nextCancion() throws SerialPortException {
        cancion_actual = cancion_actual.Sig;
        mediaPlayer.stop();
        beginTimer();
        File file = new File(cancion_actual.getDireccion());
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        if (cancion_actual.isFavorita()){
            port.writeBytes("F".getBytes());
        }
        else {
            port.writeBytes("N".getBytes());
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                songLabel.setText(file.getName());
            }
        });
        mediaPlayer.play();
    }

    @FXML
    private void pausar() {
        cancelTimer();
        mediaPlayer.pause();
    }

    private void volumen(int volumen) {
        if (this.mediaPlayer == null){
            return;
        }
        else {
        mediaPlayer.setVolume(volumen * 0.01);
        }
    }

    @FXML
    private void prevCancion() throws SerialPortException {
        cancion_actual = cancion_actual.Ant;
        mediaPlayer.stop();
        beginTimer();
        File file = new File(cancion_actual.getDireccion());
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        if (cancion_actual.isFavorita()){
            port.writeBytes("F".getBytes());
        }
        else {
            port.writeBytes("N".getBytes());
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                songLabel.setText(file.getName());
            }
        });
        mediaPlayer.play();
    }

    @FXML
    private void reproducir() throws SerialPortException {
        if (x == 0){
            File file = new File(cancion_actual.getDireccion());
            System.out.println(cancion_actual.getNombre());
            System.out.println(cancion_actual.getDireccion());

            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            if (cancion_actual.isFavorita()){
                port.writeBytes("F".getBytes());
            }
            else {
                port.writeBytes("N".getBytes());
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(file.getName());
                }
            });

            beginTimer();

            mediaPlayer.play();
            x++;
        }
        else {
            if (cancion_actual.isFavorita()){
                port.writeBytes("F".getBytes());
            }
            else {
                port.writeBytes("N".getBytes());
            }

            mediaPlayer.play();
            beginTimer();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        arduino();
        insertBiblios();
        label_usuario.setText(UsuarioSelec);

    }

    private void beginTimer(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                barraCancion.setProgress(current/end);
                if(current/end == 1){
                    if (modo_activado){
                        try {
                            nextCancion();
                        } catch (SerialPortException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        cancelTimer();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    private void cancelTimer(){
        running = false;
        timer.cancel();
    }

    SerialPort port = new SerialPort("COM3");

    private void arduino() {
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
                        if (msg.equals("M")){
                            modoContinuo();
                        }
                        if (msg.equals("T")){
                            marcarFavorita();
                        }
                        if (msg.equals("5")) {
                            volumen(0);
                        }
                        if (msg.equals("6")) {
                            volumen(5);
                        }
                        if (msg.equals("7")) {
                            volumen(15);
                        }
                        if (msg.equals("8")) {
                            volumen(40);
                        }
                        if (msg.equals("9")) {
                            volumen(75);
                        }
                        if (msg.equals("+")) {
                            volumen(115);
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


    private void insertBiblios(){
        menuSelec.getItems().clear();
       ObservableList<String> lista_biblios = FXCollections.observableArrayList();

       Biblioteca actual= new Biblioteca("");
        actual= CurrentLista.listabibliotecas.Primero;
        while(actual!=null){
            MenuItem biblio = new Menu(actual.getNombre()+"  "+ "N° Canciones: "+actual.getTamano()+"  "+actual.getFechaC());
            Biblioteca finalActual = actual;
            biblio.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    verBiblio(finalActual.getNombre());
                }
            });
            lista_biblios.add(actual.getNombre());
            biblio_a_borrar.setItems(lista_biblios);
            menuSelec.getItems().add(biblio);
            actual=actual.Sig;
        }
   }

    private void verBiblio(String x){
       Biblioteca actual= new Biblioteca(x);
       actual= CurrentLista.listabibliotecas.Primero;
       while(actual!=null){
           if (actual.getNombre().equals(x)){
               biblio_seleccionada = actual;
               verCanciones();
               break;
           }
           actual=actual.Sig;
       }

   }
    private void verCanciones(){
        ObservableList<String> list = FXCollections.observableArrayList();
       ObservableList<String> lista_edicion = FXCollections.observableArrayList();

       Cancion actual = new Cancion("","","","","","",null, null, "");
        actual = biblio_seleccionada.Primero;
        do {
            if (actual == null){
                list.add("AGREGA UNA CANCION");
                Lista_canciones.setItems(list);
                break;
            }
            list.add("Nombre: "+actual.getNombre()+"       "+"Genero: "+actual.getGen()+"       "+"Artista: "+actual.getArtista()+"       "+"Album: " +actual.getAlbum()+"       "+"Año: "+actual.getAno());
            lista_edicion.add(actual.getNombre());
            Lista_canciones.setItems(list);
            cancion_a_borrar.setItems(lista_edicion);
            cancion_a_editar.setItems(lista_edicion);
            actual = actual.Sig;

        }while (actual != biblio_seleccionada.Primero);
        cancion_actual = biblio_seleccionada.Primero;

   }

    private String obtFecha(){
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }
    @FXML
    private void CrearNBiblioteca(ActionEvent event) {
        Biblioteca biblioteca = new Biblioteca(EntryNomBi.getText());
        biblioteca.setFechaC(obtFecha());

        CurrentLista.listabibliotecas.insertBiblio(biblioteca);
        MenuItem biblio = new Menu(biblioteca.getNombre());


        menuSelec.getItems().add(biblio);
        biblio_seleccionada=biblioteca;
        CurrentLista.Guardar(UsuarioSelec);
        insertBiblios();

        biblio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                verBiblio(biblioteca.getNombre());
            }
        });
    }
    @FXML
    private void AgBohemian(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Bohemian Rhapsody");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();

        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    private void AgBones(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Bones");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();


        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    private void AgEnemy(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Enemy");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();


        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    private void AgEye(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Eye Of The Tiger");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();


        CurrentLista.Guardar(UsuarioSelec);

    }
    @FXML
    private void AgLivin(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Livin On A Prayer");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();

        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    private void AgNeverita(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Neverita");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();

        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    private void AgNothing(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Nothing Else Matters");

        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());

        Lista_canciones.setItems(list);
        insertBiblios();
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);

    }

    @FXML
    private void AgStolen(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Stolen Dance");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();


        CurrentLista.Guardar(UsuarioSelec);
    }

    @FXML
    private void AgTacones(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Tacones Rojos");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        insertBiblios();
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }

    @FXML
    private void AgTom(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Tom's Diner");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        insertBiblios();
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }


    @FXML
    private void EditarCancion(ActionEvent event) {
        String cancion = cancion_a_editar.getValue();
        Cancion actual = new Cancion("","","","","","",null, null, "");
        actual = biblio_seleccionada.Primero;
        do {
            if (actual.getNombre().equals(cancion)){
                actual.setGen(genero_editar.getText());
                actual.setArtista(artista_editar.getText());
                actual.setAlbum(album_editar.getText());
                actual.setAno(año_editar.getText());
                actual.setLetra(letra_editar.getText());
                CurrentLista.Guardar(UsuarioSelec);
                verCanciones();
                break;
            }
            else {
                actual = actual.Sig;
            }

        }while (actual != biblio_seleccionada.Primero);

    }

    @FXML
    private void borrarCancion(ActionEvent event) {
        biblio_seleccionada.eliminarCan(cancion_a_borrar.getValue());
        insertBiblios();
        verCanciones();
        CurrentLista.Guardar(UsuarioSelec);
    }

    @FXML
    private void borrarBiblioteca(ActionEvent event) {
        Biblioteca actual= new Biblioteca("");
        actual= CurrentLista.listabibliotecas.Primero;
        while(actual!=null){
            if (actual.getNombre().equals(biblio_a_borrar.getValue())){
                CurrentLista.listabibliotecas.eliminarBiblio(actual);
                CurrentLista.Guardar(UsuarioSelec);
                insertBiblios();
            }
            else {
                actual = actual.Sig;
            }
        }

    }

    private void marcarFavorita() throws SerialPortException {
        if (cancion_actual.isFavorita()){
            cancion_actual.setFavorita(false);
            CurrentLista.Guardar(UsuarioSelec);
            port.writeBytes("N".getBytes());
        }
        else {
            cancion_actual.setFavorita(true);
            CurrentLista.Guardar(UsuarioSelec);
            port.writeBytes("F".getBytes());
        }
    }
    @FXML
    void editarBiblioteca(ActionEvent event) {
        biblio_seleccionada.setNombre(nuevoNombre.getText());
        CurrentLista.Guardar(UsuarioSelec);
        insertBiblios();
    }

}
