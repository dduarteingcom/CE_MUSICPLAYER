package com.example.ce_musicplayer;

public class Biblioteca {
    public Cancion Primero;

    public Cancion Ultimo;

    public Biblioteca() {
        this.Primero = null;
        this.Ultimo = null;
    }


    public void ingresar(Cancion x) {
        //Cancion cancionN = new Cancion("", "", "", "", "", null, null, "");
        if (Primero==null){
          Primero=x;
          Primero.Sig= Primero;
          Primero.Ant= Ultimo;
          Ultimo=x;
        }
        else{
            Ultimo.Sig=x;
            x.Sig=Primero;
            Ultimo=x;
            Primero.Ant=Ultimo;
        }
    }
    public void eliminar(String x){
        Cancion actual= new Cancion("", "", "", "", "","" , null, null,"");
        Cancion prev= new Cancion("", "", "", "", "","" , null, null,"");
        actual = Primero;
        prev = Ultimo;
        do{
            if(actual.Nombre.equals(x)) {
                if (actual==Primero) {
                    Primero =Primero.Sig;
                    Ultimo.Sig=Primero;
                    Primero.Ant = Ultimo;
                }
                else if(actual==Ultimo){
                    Ultimo=prev;
                    Primero.Ant=Ultimo;
                    Ultimo.Sig=Primero;
                }
                else{
                    prev.Sig= actual.Sig;
                    actual.Sig.Ant= prev;
                }
            }
            prev=actual;
            actual=actual.Sig;

        }while(actual!=Primero);

    }
}




