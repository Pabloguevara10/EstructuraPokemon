package com.pokemon.estructuras;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioBusqueda<T extends Comparable<T>> {
    private Nodo<T> raiz;

    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private Nodo<T> insertarRecursivo(Nodo<T> actual, T dato) {
        if (actual == null) {
            return new Nodo<>(dato);
        }
        if (dato.compareTo(actual.dato) < 0) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, dato);
        } else if (dato.compareTo(actual.dato) > 0) {
            actual.derecho = insertarRecursivo(actual.derecho, dato);
        }
        return actual;
    }

    public T buscar(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    private T buscarRecursivo(Nodo<T> actual, T dato) {
        if (actual == null) {
            return null;
        }
        if (dato.compareTo(actual.dato) == 0) {
            return actual.dato;
        }
        return dato.compareTo(actual.dato) < 0
                ? buscarRecursivo(actual.izquierdo, dato)
                : buscarRecursivo(actual.derecho, dato);
    }
    
    public List<T> recorridoInOrder() {
        List<T> lista = new ArrayList<>();
        recorridoInOrderRecursivo(raiz, lista);
        return lista;
    }

    private void recorridoInOrderRecursivo(Nodo<T> nodo, List<T> lista) {
        if (nodo != null) {
            recorridoInOrderRecursivo(nodo.izquierdo, lista);
            lista.add(nodo.dato);
            recorridoInOrderRecursivo(nodo.derecho, lista);
        }
    }
}