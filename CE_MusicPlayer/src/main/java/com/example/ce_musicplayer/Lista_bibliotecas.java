package com.example.ce_musicplayer;

/**
 * Clase que corresponde a una lista simple enlazada, la cual contiene las bibliotecas que sean cargadas de alguno de los xml.
 */
public class Lista_bibliotecas {
    /**
     * Atributo de tipo Biblioteca que corresponde a la primera biblioteca de la lista.
     */
    public Biblioteca Primero;

    /**
     * Constructor que permite crear nuevas instancias de la clase Lista-bibliotecas.
     */

    public Lista_bibliotecas(){
        this.Primero=null;

    }

    /**
     * Método que inserta bibliotecas a la lista
     * @param x
     */
    public void insertBiblio(Biblioteca x){
        x.Sig=Primero;
        this.Primero= x;


    }

    /**
     * Método que se encarga de eliminar bibliotecas de la lista.
     * @param x
     */
    public void eliminarBiblio(Biblioteca x){
        Biblioteca actual= new Biblioteca("");
        actual= this.Primero;
        Biblioteca anterior = null;
        while(actual!=null){
            if(x.getNombre().equals(this.Primero.getNombre())){
                Primero=Primero.Sig;
            }
            else if (actual.Sig == null){
                break;

            }

            else if(actual.Sig.getNombre().equals(x.getNombre())){
                actual.Sig=actual.Sig.Sig;
                break;
            }

            else{
                anterior = actual;
                actual=actual.Sig;
            }
        }
    }

}
