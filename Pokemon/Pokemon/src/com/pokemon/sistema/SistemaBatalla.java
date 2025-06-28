package com.pokemon.sistema;

import com.pokemon.modelos.entrenador.Entrenador;
import com.pokemon.modelos.objetos.TipoPokeball;
import com.pokemon.modelos.pokemon.Pokemon;
import java.util.Scanner;

public class SistemaBatalla {
    private final SistemaCaptura sistemaCaptura;

    public SistemaBatalla() {
        this.sistemaCaptura = new SistemaCaptura();
    }

    public boolean iniciarBatallaSalvaje(Entrenador jugador, Pokemon salvaje, Scanner scanner) {
        System.out.println("------------------------------------------");
        System.out.println("¡Un " + salvaje.getNombre() + " salvaje apareció!");
        
        Pokemon pokemonJugador = jugador.getPrimerPokemonVivo();
        if (pokemonJugador == null) {
            System.out.println("¡No tienes Pokémon capaces de luchar!");
            return false;
        }
        System.out.println("¡Ve, " + pokemonJugador.getNombre() + "!");

        while (pokemonJugador.estaVivo() && salvaje.estaVivo()) {
            System.out.println("\n--- Turno del Jugador ---");
            System.out.println(jugador.getNombre() + ": " + pokemonJugador);
            System.out.println("Salvaje: " + salvaje);
            System.out.println("\nElige una acción:");
            System.out.println("1. Atacar");
            System.out.println("2. Usar Objeto (Pokéball)");
            System.out.println("3. Huir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            boolean turnoTerminado = false;
            if ("1".equals(opcion)) {
                int danio = pokemonJugador.getAtaque();
                salvaje.setPsActual(salvaje.getPsActual() - danio);
                System.out.println(pokemonJugador.getNombre() + " ataca. El " + salvaje.getNombre() + " salvaje pierde " + danio + " PS.");
                turnoTerminado = true;
            } else if ("2".equals(opcion)) {
                TipoPokeball bolaElegida = elegirPokeball(jugador, scanner);
                if (bolaElegida != null) {
                    jugador.usarPokeball(bolaElegida);
                    if (sistemaCaptura.intentarCaptura(salvaje, bolaElegida)) {
                        System.out.println("¡Felicidades! ¡Has capturado a " + salvaje.getNombre() + "!");
                        jugador.anadirPokemonAlEquipo(salvaje);
                        return true;
                    } else {
                        System.out.println("¡Oh, no! ¡El Pokémon se ha escapado!");
                    }
                    turnoTerminado = true;
                }
            } else if ("3".equals(opcion)) {
                System.out.println("Has huido del combate.");
                return true;
            }

            if (turnoTerminado && salvaje.estaVivo()) {
                System.out.println("\n--- Turno del Pokémon Salvaje ---");
                int danioSalvaje = salvaje.getAtaque();
                pokemonJugador.setPsActual(pokemonJugador.getPsActual() - danioSalvaje);
                System.out.println(salvaje.getNombre() + " ataca. " + pokemonJugador.getNombre() + " pierde " + danioSalvaje + " PS.");
            }
        }

        if (!pokemonJugador.estaVivo()) {
            System.out.println(pokemonJugador.getNombre() + " se ha debilitado. ¡Has perdido la batalla!");
            return false;
        }

        if (!salvaje.estaVivo()) {
            System.out.println("¡El " + salvaje.getNombre() + " salvaje se ha debilitado! ¡Has ganado!");
            int xpGanada = salvaje.getNivel() * 10;
            pokemonJugador.ganarExperiencia(xpGanada);
            return true;
        }
        return true;
    }

    private TipoPokeball elegirPokeball(Entrenador jugador, Scanner scanner) {
        System.out.println("Elige una Pokéball:");
        jugador.getInventarioPokeballs().forEach((tipo, cantidad) -> {
            if (cantidad > 0) {
                System.out.println("- " + tipo.name() + " (" + cantidad + ")");
            }
        });
        System.out.print("Escribe el nombre de la Pokéball (o 'cancelar'): ");
        String eleccion = scanner.nextLine().toUpperCase();
        try {
            TipoPokeball tipo = TipoPokeball.valueOf(eleccion);
            if (jugador.getInventarioPokeballs().getOrDefault(tipo, 0) > 0) {
                return tipo;
            } else {
                System.out.println("No tienes de ese tipo de Pokéball.");
                return null;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de Pokéball no válido.");
            return null;
        }
    }
}
