package com.example.ce_musicplayer;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Bibliotecas {
    public Biblioteca Primero;

    public Bibliotecas(){
        this.Primero=null;

    }
    public void insertBiblio(Biblioteca x){
        x.Sig=Primero;
        this.Primero= x;


    }
    public void eliminarBiblio(Biblioteca x){
        Biblioteca actual= new Biblioteca("");
        actual= this.Primero;
        while(actual!=null){
            if(x.getNombre().equals(this.Primero.getNombre())){
                Primero=Primero.Sig;
            }
            else if(actual.Sig.getNombre().equals(x.getNombre())){
                actual.Sig=actual.Sig.Sig;
                break;
            }
            else{
                actual=actual.Sig;
            }
        }
    }



}
