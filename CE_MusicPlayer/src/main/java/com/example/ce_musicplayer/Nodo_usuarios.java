package com.example.ce_musicplayer;

public class Nodo_usuarios {
    public Usuario data;
    public Nodo_usuarios next;

    public Nodo_usuarios(Usuario data, Nodo_usuarios next) {
        this.next = null;
        this.data = data;
    }

    public Usuario getData() {
        return data;
    }

    public void setData(Usuario data) {
        this.data = data;
    }

    public Nodo_usuarios getNext() {
        return next;
    }

    public void setNext(Nodo_usuarios next) {
        this.next = next;
    }
}
