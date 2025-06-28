package com.pokemon.main;

import com.pokemon.sistema.Juego;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Juego juego = new Juego(scanner);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n===== LIGA DE BATALLA POKÉMON =====");
            System.out.println("1. Gestión de Entrenadores (BST)");
            System.out.println("2. Centro de Batalla (AVL)");
            System.out.println("3. Salir del Sistema");
            System.out.print("Elige una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    menuGestionEntrenadores(juego, scanner);
                    break;
                case "2":
                    menuCentroBatalla(juego, scanner);
                    break;
                case "3":
                    juego.guardarJuego();
                    salir = true;
                    System.out.println("¡Gracias por jugar!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                    break;
            }
        }
        scanner.close();
    }

    private static void menuGestionEntrenadores(Juego juego, Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Gestión de Entrenadores (Registro Histórico) ---");
            System.out.println("1. Crear nuevo Entrenador");
            System.out.println("2. Buscar Entrenador por ID");
            System.out.println("3. Listar todos los Entrenadores");
            System.out.println("4. Volver al menú principal");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    juego.registrarNuevoEntrenador();
                    break;
                case "2":
                    juego.buscarEntrenador();
                    break;
                case "3":
                    juego.listarEntrenadores();
                    break;
                case "4":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void menuCentroBatalla(Juego juego, Scanner scanner) {
         boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Centro de Batalla (Ranking Dinámico) ---");
            System.out.println("1. Iniciar Aventura (Jugar)");
            System.out.println("2. Ver Ranking de la Liga");
            System.out.println("3. Volver al menú principal");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    juego.iniciarAventura();
                    break;
                case "2":
                    juego.mostrarRanking();
                    break;
                case "3":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
}