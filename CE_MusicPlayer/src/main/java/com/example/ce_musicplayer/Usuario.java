package com.example.ce_musicplayer;

public class Usuario {
    private String Nombre;
    private String Correo;
    private String Provincia;


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String provincia) {
        Provincia = provincia;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    private String Contrasena;

    public Usuario(String nombre, String correo, String provincia, String contrasena) {
        this.Nombre = nombre;
        this.Correo = correo;
        this.Provincia = provincia;
        this.Contrasena = contrasena;
    }
}
