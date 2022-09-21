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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.ce_musicplayer.Controlador_inicio.CurrentLista;


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
    private MenuItem itemCrearBiblio;

    @FXML
    private Menu menuCancion;

    @FXML
    private Menu menuCuenta;

    @FXML
    private Menu menuEdit;

    @FXML
    private Menu menuSelec;


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


    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        Main m = new Main();
        m.cambioEscena("Ventana_sesion.fxml");
    }

    @FXML
    void modoContinuo(ActionEvent event) {

    }

    @FXML
    void nextCancion() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(songs.get(songNumber).getName());
                }
            });
            mediaPlayer.play();

        } else {
            songNumber = 0;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(songs.get(songNumber).getName());
                }
            });

            mediaPlayer.play();
        }
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
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(songs.get(songNumber).getName());
                }
            });
            mediaPlayer.play();

        } else {
            songNumber = songs.size() - 1;
            mediaPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    songLabel.setText(songs.get(songNumber).getName());
                }
            });
            mediaPlayer.play();
        }
    }

    @FXML
    void reproducir() {
        beginTimer();
        mediaPlayer.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //obtenerCanciones();
        //arduino();
        insertBiblios();
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
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void cancelTimer(){
        running = false;
        timer.cancel();
    }


    public void arduino() {
        SerialPort port = new SerialPort("COM3");
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
                            volumen(20);
                        }
                        if (msg.equals("8")) {
                            volumen(50);
                        }
                        if (msg.equals("9")) {
                            volumen(75);
                        }
                        if (msg.equals("+")) {
                            volumen(100);
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

    public void obtenerCanciones() {
        /*

        songs = new ArrayList<File>();

        directory = new File("CE_MusicPlayer/Canciones");

        files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                songs.add(file);
                System.out.println(file);
            }
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());

         */
    }
   public void insertBiblios(){
        Biblioteca actual= new Biblioteca("");
        actual= CurrentLista.listabibliotecas.Primero;
        while(actual!=null){
            MenuItem biblio = new Menu(actual.getNombre());
            Biblioteca finalActual = actual;
            biblio.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    verBiblio(finalActual.getNombre());
                }
            });
            menuSelec.getItems().add(biblio);
            actual=actual.Sig;
        }
   }

   public void verBiblio(String x){
       Biblioteca actual= new Biblioteca(x);
       actual= CurrentLista.listabibliotecas.Primero;
       while(actual!=null){
           if (actual.getNombre().equals(x)){
               System.out.println("Biblioteca seleccionada:"+ actual.getNombre());
               biblio_seleccionada = actual;
               verCanciones();
               break;
           }
           else{actual=actual.Sig;}
       }

   }
   public void verCanciones(){
       songs = new ArrayList<File>();

       ObservableList<String> list = FXCollections.observableArrayList();
        Cancion actual = new Cancion("","","","","","",null, null, "");
        actual = biblio_seleccionada.Primero;
        do {
            System.out.println(actual.getNombre());
            list.add("Nombre: "+actual.getNombre()+"       "+"Genero: "+actual.getGen()+"       "+"Artista: "+actual.getArtista()+"       "+"Album: " +actual.getAlbum()+"       "+"Año: "+actual.getAno());
            Lista_canciones.setItems(list);
            File file = new File("CE_MusicPlayer/Canciones/Bones.mp3");
            songs.add(file);
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());

            actual = actual.Sig;
        }while (actual != biblio_seleccionada.Primero);

   }

    public void crearBiblioteca(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Nueva_biblio.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

}
