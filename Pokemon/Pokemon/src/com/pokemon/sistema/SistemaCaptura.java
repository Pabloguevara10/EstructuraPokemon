package com.pokemon.sistema;

import com.pokemon.modelos.objetos.TipoPokeball;
import com.pokemon.modelos.pokemon.Pokemon;
import java.util.Random;

public class SistemaCaptura {
    public boolean intentarCaptura(Pokemon pokemonSalvaje, TipoPokeball tipoPokeball) {
        if (tipoPokeball == TipoPokeball.MASTERBALL) return true;

        int psMax = pokemonSalvaje.getPsMax();
        int psActual = pokemonSalvaje.getPsActual();
        double multBall = tipoPokeball.getMultiplicador();
        int tasaCaptura = 45;

        double a = ((double)(3 * psMax - 2 * psActual) * tasaCaptura * multBall) / (double)(3 * psMax);
        double probabilidad = a / 256.0;

        System.out.printf("...Lanzando %s. Probabilidad de captura: %.2f%%\n", tipoPokeball.name(), probabilidad * 100);
        
        return new Random().nextDouble() <= probabilidad;
    }
}
