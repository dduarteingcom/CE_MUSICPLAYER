package com.example.ce_musicplayer;

/**
 * Lista_usuarios es una lista simple enlazada que contiene los nodos con la informacion de los usuarios
 */
public class Lista_usuarios {
    /**
     * Atributo de tipo Nodo_usuarios que corresponde a la cabeza de la lista
     */
    public Nodo_usuarios head;
    /**
     * Atributo que llevará el tamaño de la lista
     */
    private int size;

    /**
     * Metodo constructor de la lista
     */
    public Lista_usuarios() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Metodo que retorna el tamaño de la listq @return
     */
    public int size() {
        return this.size;
    }

    /**
     * Método que inserta una instancia de usuario en la primera posicion de la lista @param usuaria instancia de Usuario
     */
    public void insertarUsuario(Usuario usuario) {
        Nodo_usuarios nodo_usuarios = new Nodo_usuarios(usuario, null);
        nodo_usuarios.next = this.head;
        this.head = nodo_usuarios;
        this.size++;
    }

    /**
     * Variable global que permite acceder a la lista de usuarios en otras clases
     */
    public static Lista_usuarios lista_usuarios = new Lista_usuarios();
}
