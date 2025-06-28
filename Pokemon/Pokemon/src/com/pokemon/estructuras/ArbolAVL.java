package com.pokemon.estructuras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArbolAVL<T extends Comparable<T>> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Nodo<T> raiz;

    private int altura(Nodo<T> nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }

    private int getFactorEquilibrio(Nodo<T> nodo) {
        return nodo == null ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private Nodo<T> rotacionDerecha(Nodo<T> y) {
        Nodo<T> x = y.izquierdo;
        Nodo<T> T2 = x.derecho;
        x.derecho = y;
        y.izquierdo = T2;
        y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;
        return x;
    }

    private Nodo<T> rotacionIzquierda(Nodo<T> x) {
        Nodo<T> y = x.derecho;
        Nodo<T> T2 = y.izquierdo;
        y.izquierdo = x;
        x.derecho = T2;
        x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;
        return y;
    }

    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private Nodo<T> insertarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) return new Nodo<>(dato);

        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, dato);
        } else {
            nodo.dato = dato;
            return nodo;
        }

        nodo.altura = 1 + max(altura(nodo.izquierdo), altura(nodo.derecho));
        int factorEquilibrio = getFactorEquilibrio(nodo);

        if (factorEquilibrio > 1 && dato.compareTo(nodo.izquierdo.dato) < 0) {
            return rotacionDerecha(nodo);
        }
        if (factorEquilibrio < -1 && dato.compareTo(nodo.derecho.dato) > 0) {
            return rotacionIzquierda(nodo);
        }
        if (factorEquilibrio > 1 && dato.compareTo(nodo.izquierdo.dato) > 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }
        if (factorEquilibrio < -1 && dato.compareTo(nodo.derecho.dato) < 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }
        return nodo;
    }

    public T buscar(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    private T buscarRecursivo(Nodo<T> actual, T dato) {
        if (actual == null) return null;
        if (dato.compareTo(actual.dato) == 0) return actual.dato;
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
