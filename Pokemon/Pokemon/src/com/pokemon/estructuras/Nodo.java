package com.pokemon.estructuras;

class Nodo<T> {
    T dato;
    Nodo<T> izquierdo;
    Nodo<T> derecho;
    int altura;

    Nodo(T dato) {
        this.dato = dato;
        this.altura = 1;
    }
}