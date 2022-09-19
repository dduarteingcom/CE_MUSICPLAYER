package com.example.ce_musicplayer;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Biblioteca {
    public Cancion Primero;
    public Cancion Ultimo;
    public String Nombre;
    public Biblioteca Sig;
    public Biblioteca Ant;
    public int Tamano;
    public String FechaC;

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getFechaC() {
        return FechaC;
    }
    public void setFechaC(String fecha) {
        FechaC = fecha;
    }

    public int getTamano() {
        return Tamano;
    }

    public Biblioteca(String nombre) {
        this.Primero = null;
        this.Ultimo = null;
        this.Sig=null;
        this.Ant=null;
        this.Tamano=0;
        this.Nombre = nombre;
        this.FechaC="";
    }
    public void InsertarCan(Cancion x) {
        //Cancion cancionN = new Cancion("", "", "", "", "", null, null, "");
        if (Primero == null) {
            Primero = x;
            Ultimo = x;
            Primero.Sig = Primero;
            Ultimo.Ant = Ultimo;
            this.Tamano++;
        } else {
            Ultimo.Sig = x;
            x.Sig = Primero;
            x.Ant = Ultimo;
            Ultimo = x;
            Primero.Ant = Ultimo;
            this.Tamano++;
        }
    }

    public void eliminarCan(String x) {
        Cancion actual = new Cancion("", "", "", "", "", "", null, null, "");
        Cancion prev = new Cancion("", "", "", "", "", "", null, null, "");
        actual = Primero;
        prev = Ultimo;
        do {
            if (actual.Nombre.equals(x)) {
                if (actual == Primero) {
                    Primero = Primero.Sig;
                    Ultimo.Sig = Primero;
                    Primero.Ant = Ultimo;
                    this.Tamano--;
                } else if (actual == Ultimo) {
                    Ultimo = prev;
                    Primero.Ant = Ultimo;
                    Ultimo.Sig = Primero;
                    this.Tamano--;
                } else {
                    prev.Sig = actual.Sig;
                    actual.Sig.Ant = prev;
                    this.Tamano--;
                }
            }
            prev = actual;
            actual = actual.Sig;

        } while (actual != Primero);
    }


}








