package com.example.ce_musicplayer;

public class Cancion {
    private String Gen;



    private String Artista;
    private String Album;
    private String Ano;
    private String Letra;
    public Cancion Sig;
    public Cancion Ant;
    private String Direccion;
    public String getGen() {
        return Gen;
    }

    public void setGen(String gen) {
        Gen = gen;
    }

    public String getArtista() {
        return Artista;
    }

    public void setArtista(String artista) {
        Artista = artista;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public String getAno() {
        return Ano;
    }

    public void setAno(String ano) {
        Ano = ano;
    }

    public String getLetra() {
        return Letra;
    }

    public void setLetra(String letra) {
        Letra = letra;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public Cancion(String gen, String artista, String album, String ano, String letra, Cancion sig, Cancion ant, String direccion) {
        this.Gen = gen;
        this.Artista = artista;
        this.Album = album;
        this.Ano = ano;
        this.Letra = letra;
        this.Sig = null;
        this.Ant = null;
        this.Direccion = direccion;
    }
}
