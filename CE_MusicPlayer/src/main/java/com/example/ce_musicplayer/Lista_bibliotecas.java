package com.example.ce_musicplayer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lista_bibliotecas {
    public Biblioteca Primero;

    public Lista_bibliotecas(){
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
    public void ObtFecha() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
    }


}
