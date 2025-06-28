package com.pokemon.modelos.objetos;

import java.io.Serializable;

public enum TipoPokeball implements Serializable {
    POKEBALL(1.0),
    SUPERBALL(1.5),
    ULTRABALL(2.0),
    MASTERBALL(255.0);

    private final double multiplicador;

    TipoPokeball(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public double getMultiplicador() {
        return multiplicador;
    }
}
