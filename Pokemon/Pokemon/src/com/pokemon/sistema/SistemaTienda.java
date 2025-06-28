package com.pokemon.sistema;

import com.pokemon.modelos.entrenador.Entrenador;
import com.pokemon.modelos.objetos.TipoPokeball;
import java.util.Scanner;

public class SistemaTienda {
    private static final int PRECIO_POKEBALL = 200;
    private static final int PRECIO_SUPERBALL = 600;

    public void abrirTienda(Entrenador entrenador, Scanner scanner) {
        boolean enTienda = true;
        while(enTienda) {
            System.out.println("\n--- Tienda Pokémon ---");
            System.out.println("Tus PokéDólares: " + entrenador.getPokeDolares());
            System.out.println("1. Comprar");
            System.out.println("2. Vender Pokémon");
            System.out.println("3. Salir de la tienda");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch(opcion) {
                case "1":
                    menuComprar(entrenador, scanner);
                    break;
                case "2":
                    menuVender(entrenador, scanner);
                    break;
                case "3":
                    enTienda = false;
                    System.out.println("¡Gracias, vuelve pronto!");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private void menuComprar(Entrenador entrenador, Scanner scanner) {
        System.out.println("\n--- Comprar ---");
        System.out.println("1. Pokéball (" + PRECIO_POKEBALL + " c/u)");
        System.out.println("2. Superball (" + PRECIO_SUPERBALL + " c/u)");
        System.out.println("3. Volver");
        System.out.print("¿Qué deseas comprar?: ");
        String item = scanner.nextLine();
        
        int precio = 0;
        TipoPokeball tipo = null;

        if ("1".equals(item)) {
            precio = PRECIO_POKEBALL;
            tipo = TipoPokeball.POKEBALL;
        } else if ("2".equals(item)) {
            precio = PRECIO_SUPERBALL;
            tipo = TipoPokeball.SUPERBALL;
        } else {
            return;
        }
        
        System.out.print("¿Cuántas quieres comprar?: ");
        int cantidad = Integer.parseInt(scanner.nextLine());
        
        if (cantidad <= 0) return;
        
        int costoTotal = precio * cantidad;
        if (entrenador.gastarDinero(costoTotal)) {
            entrenador.anadirPokeballs(tipo, cantidad);
            System.out.println("Has comprado " + cantidad + " " + tipo.name() + " por " + costoTotal + " PokéDólares.");
        } else {
            System.out.println("¡No tienes suficiente dinero!");
        }
    }

    private void menuVender(Entrenador entrenador, Scanner scanner) {
        System.out.println("\n--- Vender Pokémon ---");
        if (entrenador.getEquipo().size() <= 1) {
            System.out.println("Solo tienes un Pokémon, ¡no puedes venderlo!");
            return;
        }
        
        for (int i = 0; i < entrenador.getEquipo().size(); i++) {
            System.out.println((i + 1) + ". " + entrenador.getEquipo().get(i).getNombre() + 
                               " (Valor: " + entrenador.getEquipo().get(i).getValorPrecio() + " PokéDólares)");
        }
        System.out.print("Elige el número del Pokémon a vender (o 0 para cancelar): ");
        int eleccion = Integer.parseInt(scanner.nextLine());

        if (eleccion > 0 && eleccion <= entrenador.getEquipo().size()) {
            entrenador.venderPokemon(eleccion - 1);
        }
    }
}
