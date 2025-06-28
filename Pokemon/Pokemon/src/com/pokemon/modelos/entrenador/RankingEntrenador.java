package com.pokemon.modelos.entrenador;

import java.io.Serializable;

public class RankingEntrenador implements Comparable<RankingEntrenador>, Serializable {
    private static final long serialVersionUID = 1L;

    private final int idEntrenador;
    private final String nombreEntrenador;
    private int victorias;
    private int derrotas;

    public RankingEntrenador(int idEntrenador, String nombreEntrenador) {
        this.idEntrenador = idEntrenador;
        this.nombreEntrenador = nombreEntrenador;
        this.victorias = 0;
        this.derrotas = 0;
    }

    public void registrarVictoria() { this.victorias++; }
    public void registrarDerrota() { this.derrotas++; }
    public int getIdEntrenador() { return idEntrenador; }
    public int getVictorias() { return victorias; }

    @Override
    public int compareTo(RankingEntrenador otro) {
        return Integer.compare(this.idEntrenador, otro.idEntrenador);
    }

    @Override
    public String toString() {
        return "ID: " + idEntrenador + " | " + nombreEntrenador + " | Victorias: " + victorias + " | Derrotas: " + derrotas;
    }
}