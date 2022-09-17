package com.example.ce_musicplayer;

public class Lista_usuarios {
    public Nodo_usuarios head;
    private int size;

    public Lista_usuarios() {
        this.head = null;
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public void insertarUsuario(Usuario usuario){
        Nodo_usuarios nodo_usuarios = new Nodo_usuarios(usuario, null);
        nodo_usuarios.next = this.head;
        this.head = nodo_usuarios;
        this.size++;
    }

    public static Lista_usuarios lista_usuarios = new Lista_usuarios();
}
