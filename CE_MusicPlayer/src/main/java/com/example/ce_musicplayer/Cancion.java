package com.example.ce_musicplayer;

/**
 * Esta clase almacena toda la información referente a una canción, esta posee el nombre, género, artista, album, año
 * letra y si es favorita o no. Otro aspecto de suma importancia es que contiene la dirección de la canción que será
 * requerida para ser reproducida. Además, esta clase es un nodo, ya que guarda cuál canción va antes o después de esta.
 */
public class Cancion {
    /**
     * Atributo de tipo string que almacena el género de la canción.
     */
    private String Gen;
    /**
     * Atributo de tipo string que almacena el nombre de la canción.
     */
    public String Nombre;
    /**
     * Atributo de tipo string que almacena el artista de la canción.
     */
    private String Artista;
    /**
     * Atributo de tipo string que almacena el álbum de la canción.
     */
    private String Album;
    /**
     * Atributo de tipo string que almacena el año de la canción.
     */
    private String Ano;
    /**
     * Atributo de tipo string que almacena la letra de la canción.
     */

    private String Letra;
    /**
     * Atributo de tipo string que almacena la dirección de la canción.
     */
    private String Direccion;
    /**
     * Atributo de tipo Cancion que almacena la canción que sigue.
     */
    public Cancion Sig;
    /**
     * Atributo de tipo Cancion que almacena la canción anterior.
     */
    public Cancion Ant;
    /**
     * Atributo de tipo Boolean que almacena si la canción es favorita o no.
     */
    public boolean Favorita;

    /**
     * Método que retorna el String que corresponde al género de la canción.
     * @return Género de la canción.
     */
    public String getGen() {
        return Gen;
    }

    /**
     * Método que modifica el género de la canción.
     * @param gen
     */
    public void setGen(String gen) {
        Gen = gen;
    }
    /**
     * Método que retorna el String que corresponde al artista de la canción.
     * @return Artista de la canción.
     */
    public String getArtista() {
        return Artista;
    }
    /**
     * Método que modifica el artista de la canción.
     * @param artista
     */
    public void setArtista(String artista) {
        Artista = artista;
    }
    /**
     * Método que retorna el String que corresponde al album de la canción.
     * @return Album de la canción.
     */
    public String getAlbum() {
        return Album;
    }
    /**
     * Método que modifica el album de la canción.
     * @param album
     */
    public void setAlbum(String album) {
        Album = album;
    }
    /**
     * Método que retorna el String que corresponde al año de lanzamiento de la canción.
     * @return Año de la canción.
     */
    public String getAno() {
        return Ano;
    }
    /**
     * Método que modifica el año de lanzamiento de la canción.
     * @param ano
     */
    public void setAno(String ano) {
        Ano = ano;
    }
    /**
     * Método que retorna el String que corresponde a la letra de la canción.
     * @return Letra de la canción.
     */
    public String getLetra() {
        return Letra;
    }
    /**
     * Método que modifica la letra de la canción.
     * @param letra
     */
    public void setLetra(String letra) {
        Letra = letra;
    }
    /**
     * Método que retorna el String que corresponde a la Dirección de la canción.
     * @return Dirección de la canción.
     */
    public String getDireccion() {
        return Direccion;
    }
    /**
     * Método que modifica la dirección de la canción.
     * @param direccion
     */
    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
    /**
     * Método que retorna el String que corresponde al nombre de la canción.
     * @return Letra de la canción.
     */
    public String getNombre() {
        return Nombre;
    }
    /**
     * Método que modifica el nombre de la canción.
     * @param nombre
     */
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    /**
     * Método que indica si la cancion es favorita
     * @return true o false
     */
    public boolean isFavorita() {
        return Favorita;
    }
    /**
     * Método que modifica el valor buleano del atributo Favorita.
     * @param favorita
     */
    public void setFavorita(boolean favorita) {
        Favorita = favorita;
    }

    /**
     * Constructor empleado para cada vez que se desee crear una instancia de la clase canción.
     * @param nombre
     * @param gen
     * @param artista
     * @param album
     * @param ano
     * @param letra
     * @param sig
     * @param ant
     * @param direccion
     */
    public Cancion(String nombre, String gen, String artista, String album, String ano, String letra, Cancion sig, Cancion ant, String direccion) {
        this.Nombre= nombre;
        this.Gen = gen;
        this.Artista = artista;
        this.Album = album;
        this.Ano = ano;
        this.Letra = letra;
        this.Sig = null;
        this.Ant = null;
        this.Direccion = direccion;
        this.Favorita= false;
    }

}
