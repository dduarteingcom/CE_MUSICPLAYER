package com.example.ce_musicplayer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private Button modoButton, nextButton, pauseButton, playButton, prevButton;
    /**
     * Label que muestra el nombre de la canción que se está reproduciendo.
     */
    @FXML
    private Label songLabel;
    /**
     * Opcion de menu que muestra las canciones para agregar
     */
    @FXML
    private Menu menuCancion, menuCuenta, menuSelec;
    /**
     * Boton utilizado para la creacion de una nueva biblioteca
     */
    @FXML
    private Button BotCrearBi, editBiblioButton;
    /**
     * Entrada de texto utilizada para cambiar el nombre de una biblioteca
     */
    @FXML
    private TextField nuevoNombre, EntryNomBi;
    /**
     * Pestaña que contiene los atributos para crear una nueva biblioteca.
     */
    @FXML
    private TitledPane VenCBiblioteca;
    /**
     * Boton para agregar una cancion a la biblioteca
     */
    @FXML
    private Button BBohemian, BBones, BEnemy, BEye, BLivin, BNeverita, BNothing, BStolen, BTacones, BTom;
    /**
     * Items de menu para seleccionar una cancion para agregar
     */
    @FXML
    private MenuItem LBohemian, LBones, LEnemy, LEye, LLivin, LNeverita, LNothing, LStolen, LTacones, LTom;
    /**
     * Label que muestra el nombre del usuario que ha iniciado sesion
     */
    @FXML
    private Label label_usuario;
    /**
     * Entradas de textos para editar el albun, artista y año de una cancion
     */
    @FXML
    private TextField album_editar, artista_editar, año_editar;
    /**
     * Lista predefinida que muestra las canciones de una biblioteca para editar
     */
    @FXML
    private ChoiceBox<String> cancion_a_editar;
    /**
     * Boton que ditar el nombre de la biblioteca
     */
    @FXML
    private Button editarButton;
    /**
     * Entrada de texto para editar el género de una cancion
     */
    @FXML
    private TextField genero_editar;
    /**
     * Boton para borrar una cancion de una biblioteca
     */
    @FXML
    private Button borrar_button;
    /**
     * Listas predefinidas para elegir una cancion o bibliotecas para borrar
     */
    @FXML
    private ChoiceBox<String> cancion_a_borrar, biblio_a_borrar;
    /**
     * Boton utilizado para borrar una biblioteca
     */
    @FXML
    private Button borrar_biblioButton;
    /**
     * Entrada de texto para editar la letra de una cancion
     */
    @FXML
    private TextField letra_editar;
    /**
     * Atributo necesario para guardar el archivo de reproduccion
     */
    private Media media;
    /**
     * Atributo necesario para la reproduccion de sonido
     */
    private MediaPlayer mediaPlayer;
    /**
     * Atributo que llevará el tiempo de la canción actual
     */
    private Timer timer;
    /**
     * Labels usados como mensajes de error en caso de dejar un espacio vacio
     */
    @FXML
    private Label errorBiblio, errorEditBiblio, errorEditCancion;
    /**
     * Boton utilizado para marcar una cancion como favorita
     */
    @FXML
    private Button buttonFav;
    /**
     * Label utilizado para mostrar la letra de la cancion actual
     */
    @FXML
    private Label labelLyrics;
    /**
     * Panel deslizable que va a contener la letra de la cancion actual
     */
    @FXML
    private ScrollPane lyricsPane;
    /**
     * Atributo necesario para llevar el control del tiempo de reproduccion
     */
    private TimerTask task;
    /**
     * Atributo booleano para identificar si el tiempo corre cuando se reproduce una cancion
     */
    private boolean running;
    /**
     * Atributo de tipo Biblioteca para saber cuál es la biblioteca seleccionada
     */
    private Biblioteca biblio_seleccionada;
    /**
     * Atributo de tipo Cancion que indica la cancion que se está reproduciendo actualmente
     */
    private Cancion cancion_actual;
    /**
     * Atributo de tipo boolean que indica cuando se tiene que crear una cancion cuando se da a reproducir por primera vez
     */
    private boolean x = false, modo_activado = false;
    /**
     * Atributo utilizado para asignar los archivos mp3 para su reproduccion
     */
    private File file;
    /**
     * Lista observable usada para mostrar una lista de canciones de una biblioteca
     */
    private ObservableList<String> list = FXCollections.observableArrayList();

    /**
     * Método que permite al usuario cerrar sesion e ir a la pantalla de inicio de sesion
     * @param event @throws IOException
     * @throws SerialPortException
     */
    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException, SerialPortException {
        Main m = new Main();
        port.closePort();
        m.cambioEscena("Ventana_sesion.fxml");
    }

    /**
     * Método que permite activar o desactivar el modo continuo @throws SerialPortException
     */
    @FXML
    private void modoContinuo() throws SerialPortException {
        if (modo_activado) {
            modo_activado = false;
            System.out.println("Modo continuo desactivado");
            port.writeBytes("R".getBytes());
        } else {
            modo_activado = true;
            System.out.println("Modo continuo activado");
            port.writeBytes("T".getBytes());
        }
    }

    /**
     * Metodo que reproduce la siguiente cancion de la biblioteca seleccionada @throws SerialPortException
     */
    @FXML
    private void nextCancion() throws SerialPortException {
        //Se pasa a la siguiente cancion de la lista circular
        cancion_actual = cancion_actual.Sig;
        //Paramos la cancion que está sonando actualmente
        mediaPlayer.stop();
        //Iniciamos el tiempo
        beginTimer();
        //Cargamos el archivo de la siguiente cancion para posteriormente reproducirla
        File file = new File(cancion_actual.getDireccion());
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //Verifica si la cancion es favorita o no para posteriormente enviar un mensaje al arduino
        if (cancion_actual.isFavorita()) port.writeBytes("F".getBytes());
        else port.writeBytes("N".getBytes());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Mostramos el nombre de la nueva cancion a reproducir
                songLabel.setText(file.getName());
                //Mostramos la letra de la nueva cancion
                labelLyrics.setText(cancion_actual.getLetra() + "\n");
            }
        });
        //Reproducimos la cancion
        mediaPlayer.play();
    }

    /**
     * Metodo que pausa la cancion actual
     */
    @FXML
    private void pausar() {
        //Paramos el tiempo
        cancelTimer();
        //Pausamos la reproduccion
        mediaPlayer.pause();
    }

    /**
     * Método que recibe un número y modifica el volumen de la canción con base en ese número
     * @param volumen numero que modifica el modifica el volumen
     */
    private void volumen(int volumen) {
        //Verifica que la reproduccion esté vacía
        if (this.mediaPlayer == null) return; //No retorna nada
        //Modificamos el volumen
        else mediaPlayer.setVolume(volumen * 0.01);
    }

    /**
     * Metodo que permite al usuario regresar a la cancion anterior de la biblioteca. @throws SerialPortException
     */
    @FXML
    private void prevCancion() throws SerialPortException {
        //Se pasa a la cancion anterior de la lista circular
        cancion_actual = cancion_actual.Ant;
        //Paramos la cancion que está sonando actualmente
        mediaPlayer.stop();
        //Iniciamos el tiempo
        beginTimer();
        //Cargamos el archivo de la cancion anterior para posteriormente reproducirla
        File file = new File(cancion_actual.getDireccion());
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //Verifica si la cancion es favorita o no para posteriormente enviar un mensaje al arduino
        if (cancion_actual.isFavorita()) port.writeBytes("F".getBytes());
        else port.writeBytes("N".getBytes());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Mostramos el nombre de la nueva cancion a reproducir
                songLabel.setText(file.getName());
                //Mostramos la letra de la nueva cancion
                labelLyrics.setText(cancion_actual.getLetra() + "\n");
            }
        });
        mediaPlayer.play();
    }

    /**
     * Metodo que reproduce la cancion actual @throws SerialPortException
     */
    @FXML
    private void reproducir() throws SerialPortException {
        //Verifica si es la primera cancion a reproducir
        if (x == false) {
            //Cargamos el archivo para reproducir
            File file = new File(cancion_actual.getDireccion());
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            //Verifica si la cancion es favorita o no para posteriormente enviar un mensaje al arduino
            if (cancion_actual.isFavorita()) port.writeBytes("F".getBytes());
            else port.writeBytes("N".getBytes());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //Mostramos el nombre de la nueva cancion a reproducir
                    songLabel.setText(file.getName());
                    //Mostramos la letra de la nueva cancion

                    labelLyrics.setText(cancion_actual.getLetra());
                    labelLyrics.setTextAlignment(TextAlignment.LEFT);
                }
            });
            //Iniciamos el tiempo
            beginTimer();
            //Reproducimos la cancion
            mediaPlayer.play();
            //Cambiamos la variable para indicar que ya no es la primera vez que se reproduce una cancion
            x = true;
        }
        //Si no es la primera vez que se reproduce la cancion, solo reaunudaremos la cancion
        else {
            if (cancion_actual.isFavorita()) port.writeBytes("F".getBytes());
            else port.writeBytes("N".getBytes());
            mediaPlayer.play();
            beginTimer();
        }
    }

    /**
     * Método que invoca a otros métodos para que funcionen en conjunto a la ventana
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        arduino();
        insertBiblios();
        label_usuario.setText(UsuarioSelec);
    }

    /**
     * Función que lleva el control de tiempo de la reproduccion de la cancion actual e identifica si el modo continuo está activado para pasar a la siguiente cancion o, por el contrario, se detenga el tiempo al finalizar la cancion.
     */
    private void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //Iniciamos la variable que permite correr el tiempo
                running = true;
                //Obtenemos el tiempo actual
                double current = mediaPlayer.getCurrentTime().toSeconds(), end = media.getDuration().toSeconds();
                barraCancion.setProgress(current / end);
                //Cuando el tiempo llegue a su fin
                if (current / end == 1) if (modo_activado) try {
                    //Si el modo continuo está activado, se pasará a la siguiente canción
                    nextCancion();
                } catch (SerialPortException e) {
                    throw new RuntimeException(e);
                }
                //Si el modo continuo está desactivado, se detendrá el tiempo
                else cancelTimer();
            }
        };
        //Definimos que el tiempo avance por segundos
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    /**
     * Método que para el tiempo de la funcion beginTimer.
     */
    private void cancelTimer() {
        running = false;
        timer.cancel();
    }

    /**
     * Variable utilizada para manejar el puerto serial del arduino
     */
    SerialPort port = new SerialPort("COM3");

    /**
     * Método que lee el arduino y sus componentes y llame otro método según el mensaje leído por arduino.
     */
    private void arduino() {
        try {
            //Abrimos el puerto
            port.openPort();
            //Definimos los parametros
            port.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            port.addEventListener((SerialPortEvent event) -> {
                if (event.isRXCHAR()) try {
                    //Leemos las líneas que envia arduino
                    String msg = port.readString();
                    //Según el mensaje leído, se llamará a una función
                    if (msg.equals("1")) reproducir();
                    if (msg.equals("2")) pausar();
                    if (msg.equals("3")) nextCancion();
                    if (msg.equals("4")) prevCancion();
                    if (msg.equals("M")) modoContinuo();
                    if (msg.equals("T")) marcarFavorita();
                    if (msg.equals("5")) volumen(0);
                    if (msg.equals("6")) volumen(5);
                    if (msg.equals("7")) volumen(15);
                    if (msg.equals("8")) volumen(40);
                    if (msg.equals("9")) volumen(75);
                    if (msg.equals("+")) volumen(115);
                } catch (SerialPortException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que inserta las bibliotecas del usuario como items para el menú de "Seleccionae biblioteca"
     */
    private void insertBiblios() {
        //Vaciamos el menu con las bibliotecas para ser actualizada
        menuSelec.getItems().clear();
        //Lista que se utilizara para agregar el nombre de las bibliotecas en la pestaña para borrar una biblioteca
        ObservableList<String> lista_biblios = FXCollections.observableArrayList();
        Biblioteca actual = CurrentLista.listabibliotecas.Primero;
        //Recorremos la lista de bibliotecas
        while (actual != null) {
            //Creamos items de menu por cada biblioteca del usuario
            MenuItem biblio = new Menu(actual.getNombre() + "  " + "N° Canciones: " + actual.getTamano() + "  " + actual.getFechaC());
            Biblioteca finalActual = actual;
            biblio.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                //Asignamos a cada item de menu de bibliotecas una funcion
                public void handle(ActionEvent actionEvent) {
                    verBiblio(finalActual.getNombre());
                }
            });
            //Añadimos el nombre de la biblioteca a la lista
            lista_biblios.add(actual.getNombre());
            //Añadimos el nombre de las bibliotecas
            biblio_a_borrar.setItems(lista_biblios);
            //Añadimos las bibliotecas al menú
            menuSelec.getItems().add(biblio);
            actual = actual.Sig;
        }
    }

    /**
     * Método que recibe el nombre de una biblioteca seleccionada y la busca dicha biblioteca en la lista para posteriormente leer sus canciones
     * @param x nombre de la biblioteca a buscar
     */
    private void verBiblio(String x) {
        Biblioteca actual = CurrentLista.listabibliotecas.Primero;
        while (actual != null) {
            if (actual.getNombre().equals(x)) {
                biblio_seleccionada = actual;
                //Una vez la biblioteca seleccionada fue encontrada, se procede a ver sus canciones
                verCanciones();
                break;
            }
            actual = actual.Sig;
        }
    }

    /**
     * Metodo que lee las canciones de una biblioteca previamente seleccionada y las muestra en una lista
     */
    private void verCanciones() {
        //Lista que mostrará los datos de las canciones
        ObservableList<String> list = FXCollections.observableArrayList(), lista_edicion = FXCollections.observableArrayList();
        Cancion actual = biblio_seleccionada.Primero;
        do {
            //En caso de que la biblioteca esté vacía
            if (actual == null) {
                list.add("AGREGA UNA CANCION");
                Lista_canciones.setItems(list);
                break;
            }
            //Agregamos las canciones en las listas para mostrarlas, editarlas o borrarlas.
            list.add("Nombre: " + actual.getNombre() + "       " + "Genero: " + actual.getGen() + "       " + "Artista: " + actual.getArtista() + "       " + "Album: " + actual.getAlbum() + "       " + "Año: " + actual.getAno());
            lista_edicion.add(actual.getNombre());
            Lista_canciones.setItems(list);
            cancion_a_borrar.setItems(lista_edicion);
            cancion_a_editar.setItems(lista_edicion);
            actual = actual.Sig;
        } while (actual != biblio_seleccionada.Primero);
        cancion_actual = biblio_seleccionada.Primero;
    }

    /**
     * Método que retorna la fecha y hora de la computadora cuando se solicite.
     * @return Fecha y hora actual.
     */
    private String obtFecha() {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    /**
     * Metodo que permite crear una nueva biblioteca
     * @param event
     */
    @FXML
    private void CrearNBiblioteca(ActionEvent event) {
        //Obligamos al usuario llenar la entrada de texto.
        if (EntryNomBi.getText().isEmpty()) errorBiblio.setText("Escribe un nombre");
        else {
            //Creamos la biblioteca
            Biblioteca biblioteca = new Biblioteca(EntryNomBi.getText());
            biblioteca.setFechaC(obtFecha());
            CurrentLista.listabibliotecas.insertBiblio(biblioteca);
            MenuItem biblio = new Menu(biblioteca.getNombre());
            menuSelec.getItems().add(biblio);
            biblio_seleccionada = biblioteca;
            CurrentLista.Guardar(UsuarioSelec);
            //Actualizamos la lista de bibliotecas para que la nueva biblioteca creada salga en pantalla.
            insertBiblios();
            biblio.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    verBiblio(biblioteca.getNombre());
                }
            });
        }
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgBohemian(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Bohemian Rhapsody");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgBones(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Bones");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgEnemy(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Enemy");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgEye(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Eye Of The Tiger");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgLivin(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Livin On A Prayer");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgNeverita(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Neverita");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgNothing(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Nothing Else Matters");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        insertBiblios();
        verCanciones();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgStolen(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Stolen Dance");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        verCanciones();
        insertBiblios();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgTacones(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Tacones Rojos");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        insertBiblios();
        verCanciones();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Método que agrega la cancion seleccionada a una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void AgTom(ActionEvent event) {
        biblio_seleccionada.InsertnewSong("Tom's Diner");
        list.add("Nombre: " + biblio_seleccionada.Ultimo.getNombre() + "       " + "Genero: " + biblio_seleccionada.Ultimo.getGen() + "       " + "Artista: " + biblio_seleccionada.Ultimo.getArtista() + "       " + "Album: " + biblio_seleccionada.Ultimo.getAlbum() + "       " + "Año: " + biblio_seleccionada.Ultimo.getAno());
        Lista_canciones.setItems(list);
        insertBiblios();
        verCanciones();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Metodo que modifica los atributos de una cancion seleccionada
     * @param event
     */
    @FXML
    private void EditarCancion(ActionEvent event) {
        //Obligamos al usuario a no dejar las entries vacias.
        if (genero_editar.getText().isEmpty() || artista_editar.getText().isEmpty() || album_editar.getText().isEmpty() || año_editar.getText().isEmpty() || letra_editar.getText().isEmpty())
            errorEditCancion.setText("Llena todos los espacios");
        else {
            String cancion = cancion_a_editar.getValue();
            Cancion actual = biblio_seleccionada.Primero;
            //Buscamos la cancion que se quiera editar
            do if (actual.getNombre().equals(cancion)) {
                //Modificamos los atributos
                actual.setGen(genero_editar.getText());
                actual.setArtista(artista_editar.getText());
                actual.setAlbum(album_editar.getText());
                actual.setAno(año_editar.getText());
                actual.setLetra(letra_editar.getText());
                CurrentLista.Guardar(UsuarioSelec);
                verCanciones();
                break;
            } else actual = actual.Sig; while (actual != biblio_seleccionada.Primero);
        }
    }

    /**
     * Metodo que borra una cancion seleccionada de una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void borrarCancion(ActionEvent event) {
        //Borramos la cancion
        biblio_seleccionada.eliminarCan(cancion_a_borrar.getValue());
        //Actualizamos las bibliotecas y la lista de cancion para que ya no aparezca la cancion recientemente eliminada.
        insertBiblios();
        verCanciones();
        CurrentLista.Guardar(UsuarioSelec);
    }

    /**
     * Metodo que permite borrar una biblioteca seleccionada
     * @param event
     */
    @FXML
    private void borrarBiblioteca(ActionEvent event) {
        Biblioteca actual = CurrentLista.listabibliotecas.Primero;
        while (actual != null) if (actual.getNombre().equals(biblio_a_borrar.getValue())) {
            //Eliminamos la biblioteca
            CurrentLista.listabibliotecas.eliminarBiblio(actual);
            CurrentLista.Guardar(UsuarioSelec);
            //Actualizamos las bibliotecas para que no aparezca la biblioteca recientemente eliminada
            insertBiblios();
        } else actual = actual.Sig;
    }

    /**
     * Metodo que permite marcar una cancion como canción favorita y la reconozca para así enviar un mensaje a Arduino.
     * @throws SerialPortException
     */
    private void marcarFavorita() throws SerialPortException {
        //En caso de que la canción ya se encuentra como favorita
        if (cancion_actual.isFavorita()) {
            //La desmarcamos como favorita
            cancion_actual.setFavorita(false);
            CurrentLista.Guardar(UsuarioSelec);
            //Enviamos un mensaje al arduino para que reconozca que la cancion ya no es favorita
            port.writeBytes("N".getBytes());
        }
        //En caso de que la cancion no esté marcada como favorita
        else {
            //Marcamos la cancion como favorita
            cancion_actual.setFavorita(true);
            CurrentLista.Guardar(UsuarioSelec);
            //Enviamos un mensaje al arduino para que reconozca que la cancion es favorita
            port.writeBytes("F".getBytes());
        }
    }

    /**
     * Metodo que permite cambiar el nombre de una biblioteca seleccionada
     * @param event
     */
    @FXML
    void editarBiblioteca(ActionEvent event) {
        //Obligamos al usuario a no dejar la entrada de texto vacía
        if (nuevoNombre.getText().isEmpty()) errorEditBiblio.setText("Escribe un nombre");
        else {
            errorEditBiblio.setText("");
            biblio_seleccionada.setNombre(nuevoNombre.getText());
            CurrentLista.Guardar(UsuarioSelec);
            insertBiblios();
        }
    }

    /**
     * Función que invoca a la funcion de marcarFavorita cuando se presiona el boton desde la ventana de reproduccion
     * @param event
     * @throws SerialPortException
     */
    @FXML
    void marcarFav(ActionEvent event) throws SerialPortException {
        marcarFavorita();
    }
}
