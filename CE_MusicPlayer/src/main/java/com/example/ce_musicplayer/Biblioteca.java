package com.example.ce_musicplayer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Biblioteca {
    public Cancion Primero;

    public Cancion Ultimo;
    public String Nombre;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Biblioteca(String nombre) {
        this.Primero = null;
        this.Ultimo = null;
        this.Nombre = nombre;
    }


    public void ingresar(Cancion x) {
        //Cancion cancionN = new Cancion("", "", "", "", "", null, null, "");
        if (Primero==null){
          Primero=x;
          Ultimo= x;
          Primero.Sig= Primero;
          Ultimo.Ant=Ultimo;
        }
        else{
            Ultimo.Sig=x;
            x.Sig=Primero;
            x.Ant=Ultimo;
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

    public void Guardar(){
        try {
            FileWriter writer = new FileWriter("C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\src\\main\\java\\com\\example\\ce_musicplayer\\Prueba.txt");
            PrintWriter pw =new PrintWriter(writer);
            pw.println(Nombre+"\n");
            Cancion Actual= new Cancion("", "", "", "", "","" , null, null,"");
            Actual=Ultimo;
            if(Ultimo!=null){
                do{
                    pw.println( Actual.getNombre() + Actual.getGen() + Actual.getArtista() + Actual.getAlbum()
                    +Actual.getAno() + Actual.getLetra() + Actual.getDireccion());
                    Actual= Actual.Ant;

                }while(Actual!=Ultimo);
            }
            else{
                System.out.println("La Biblioteca se encuentra vacia");
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void Display(){
        Cancion Actual= new Cancion("", "", "", "", "","" , null, null,"");
        Actual=Ultimo;
        if(Ultimo!=null){
            do{
                System.out.println(Actual.Nombre);
                Actual=Actual.Ant;
            }while (Actual!=Ultimo);
        }
        else{
            System.out.println("La lista se encuentra vacia");
        }
    }
}




