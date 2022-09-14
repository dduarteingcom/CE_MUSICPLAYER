package com.example.ce_musicplayer;

public class Biblioteca {
    private Cancion Primero;

    private Cancion Ultimo;

    public Biblioteca() {
        this.Primero = null;
        this.Ultimo = null;
    }


    public void ingresar(Cancion x) {
        Cancion cancionN = new Cancion("", "", "", "", "", null, null, "");
        if (Primero==null){
          Primero=cancionN;
          Primero.Sig= Primero;
          Primero.Ant= Ultimo;
          Ultimo=cancionN;
        }
        else{
            Ultimo.Sig=cancionN;
            cancionN.Sig=Primero;
            Ultimo=cancionN;
            Primero.Ant=Ultimo;
        }
    }

}




