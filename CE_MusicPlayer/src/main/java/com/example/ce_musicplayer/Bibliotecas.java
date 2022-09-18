package com.example.ce_musicplayer;

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
    Bibliotecas BMauricio=new Bibliotecas();
    Bibliotecas BDaniel=new Bibliotecas();
    Bibliotecas BMbappe=new Bibliotecas();

}
