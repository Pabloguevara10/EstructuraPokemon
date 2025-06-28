package com.pokemon.estructuras;

import java.io.Serializable;

class Nodo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    T dato;
    Nodo<T> izquierdo;
    Nodo<T> derecho;
    int altura;

    Nodo(T dato) {
        this.dato = dato;
        this.altura = 1;
    }
}
