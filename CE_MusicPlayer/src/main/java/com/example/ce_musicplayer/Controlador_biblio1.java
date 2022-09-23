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
    private ListView<String> Lista_canciones;

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
    private Menu menuCancion;

    @FXML
    private Menu menuCuenta;

    @FXML
    private Menu menuEdit;

    @FXML
    private Menu menuSelec;
    @FXML
    private Button BotCrearBi;

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


    private Media media;
    private MediaPlayer mediaPlayer;

    private File directory;
    private File[] files;

    private ArrayList<File> songs;

    private Timer timer;
    private TimerTask task;
    private boolean running;
    private int songNumber;

    String[] canciones;

    private Biblioteca biblio_seleccionada;
    private Cancion cancion_actual;

    private int x = 0;

    private boolean modo_activado = false;
    private File file;
    private ObservableList<String> list = FXCollections.observableArrayList();



    @FXML
    void cerrarSesion(ActionEvent event) throws IOException, SerialPortException {
        Main m = new Main();
        port.closePort();
        m.cambioEscena("Ventana_sesion.fxml");
    }

    @FXML
    void modoContinuo(ActionEvent event) {
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
    void nextCancion() {
        cancion_actual = cancion_actual.Sig;
        mediaPlayer.stop();
        beginTimer();
        File file = new File(cancion_actual.getDireccion());
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                songLabel.setText(file.getName());
            }
        });
        mediaPlayer.play();
    }

    @FXML
    void pausar() {
        cancelTimer();
        mediaPlayer.pause();
    }

    void volumen(int volumen) {
        if (this.mediaPlayer == null){
            return;
        }
        else {
        mediaPlayer.setVolume(volumen * 0.01);
        }
    }

    @FXML
    void prevCancion() {
        cancion_actual = cancion_actual.Ant;
        mediaPlayer.stop();
        beginTimer();
        File file = new File(cancion_actual.getDireccion());
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                songLabel.setText(file.getName());
            }
        });
        mediaPlayer.play();
    }

    @FXML
    void reproducir() throws SerialPortException {
        if (x == 0){
            File file = new File(cancion_actual.getDireccion());
            System.out.println(cancion_actual.getNombre());
            System.out.println(cancion_actual.getDireccion());

            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
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
            mediaPlayer.play();
            beginTimer();
        }

        /*
        System.out.println(songs.get(songNumber).getName());
        mediaPlayer.play();

         */

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //obtenerCanciones();
        arduino();
        insertBiblios();
        label_usuario.setText(UsuarioSelec);

    }

    public void beginTimer(){
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
                        nextCancion();
                        songLabel.setText(file.getName());
                    }
                    else {
                        cancelTimer();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void cancelTimer(){
        running = false;
        timer.cancel();
    }

    SerialPort port = new SerialPort("COM4");

    public void arduino() {
        try {
            port.openPort();

            port.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            port.addEventListener((SerialPortEvent event) -> {
                if (event.isRXCHAR()) {
                    try {
                        String msg = port.readString();
                        System.out.println(msg);
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


   public void insertBiblios(){
        menuSelec.getItems().clear();
       ObservableList<String> lista_biblios = FXCollections.observableArrayList();

       Biblioteca actual= new Biblioteca("");
        actual= CurrentLista.listabibliotecas.Primero;
        while(actual!=null){
            MenuItem biblio = new Menu(actual.getNombre()+"  "+actual.getFechaC());
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

   public void verBiblio(String x){
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
   public void verCanciones(){
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

    public String obtFecha(){
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }
    @FXML
    void CrearNBiblioteca(ActionEvent event) {
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
    void AgBohemian(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Bohemian Rhapsody");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    void AgBones(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Bones");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    void AgEnemy(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Enemy");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    void AgEye(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Eye Of The Tiger");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);

    }
    @FXML
    void AgLivin(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Livin On A Prayer");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    void AgNeverita(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Neverita");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }
    @FXML
    void AgNothing(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Nothing Else Matters");

        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());

        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);

    }

    @FXML
    void AgStolen(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Stolen Dance");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }

    @FXML
    void AgTacones(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Tacones Rojos");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }

    @FXML
    void AgTom(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Tom's Diner");
        list.add("Nombre: "+biblio_seleccionada.Ultimo.getNombre()+"       "+"Genero: "
                +biblio_seleccionada.Ultimo.getGen()+"       "+"Artista: "+biblio_seleccionada.Ultimo.getArtista()+
                "       "+"Album: " +biblio_seleccionada.Ultimo.getAlbum()+"       "+"Año: "
                +biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();

        CurrentLista.Guardar(UsuarioSelec);
    }


    @FXML
    void EditarCancion(ActionEvent event) {


    }

    @FXML
    void borrarCancion(ActionEvent event) {
        biblio_seleccionada.eliminarCan(cancion_a_borrar.getValue());
        verCanciones();
        CurrentLista.Guardar(UsuarioSelec);
    }

    @FXML
    void borrarBiblioteca(ActionEvent event) {
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






}
