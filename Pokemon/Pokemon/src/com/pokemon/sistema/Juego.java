package com.pokemon.sistema;

import com.pokemon.estructuras.ArbolAVL;
import com.pokemon.estructuras.ArbolBinarioBusqueda;
import com.pokemon.modelos.entrenador.Entrenador;
import com.pokemon.modelos.entrenador.RankingEntrenador;
import com.pokemon.modelos.pokemon.Pokemon;
import com.pokemon.modelos.pokemon.TipoPokemon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Juego {
    private ArbolBinarioBusqueda<Entrenador> registroHistorico;
    private ArbolAVL<RankingEntrenador> rankingLiga;
    private final SistemaBatalla sistemaBatalla;
    private final SistemaTienda sistemaTienda;
    private final List<Pokemon> pokemonSalvajesDisponibles;
    private int proximoIdEntrenador = 1;
    private final Scanner scanner;

    public Juego(Scanner scanner) {
        this.scanner = scanner;
        this.sistemaBatalla = new SistemaBatalla();
        this.sistemaTienda = new SistemaTienda();
        this.pokemonSalvajesDisponibles = new ArrayList<>();
        
        // Se inicializan las estructuras de datos directamente, sin cargar desde archivo
        this.registroHistorico = new ArbolBinarioBusqueda<>();
        this.rankingLiga = new ArbolAVL<>();
        
        inicializarPokemonSalvajes();
    }
    
    // El método para guardar ya no hace nada
    public void guardarJuego() {
        // La funcionalidad de guardado ha sido removida
    }

    private void inicializarPokemonSalvajes() {
        pokemonSalvajesDisponibles.add(new Pokemon("Pidgey", TipoPokemon.NORMAL, 40, 5, 5, 3));
        pokemonSalvajesDisponibles.add(new Pokemon("Rattata", TipoPokemon.NORMAL, 35, 6, 4, 2));
        pokemonSalvajesDisponibles.add(new Pokemon("Caterpie", TipoPokemon.PLANTA, 45, 4, 4, 2));
    }
    
    public void registrarNuevoEntrenador() {
        System.out.print("Introduce tu nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduce tu correo: ");
        String correo = scanner.nextLine();
        
        Entrenador nuevoEntrenador = new Entrenador(proximoIdEntrenador, nombre, correo);
        registroHistorico.insertar(nuevoEntrenador);

        Pokemon pokemonInicial = new Pokemon("Pikachu", TipoPokemon.ELECTRICO, 50, 8, 5, 5);
        nuevoEntrenador.anadirPokemonAlEquipo(pokemonInicial);
        
        System.out.println("¡Bienvenido, " + nombre + "! Tu ID de Entrenador es: " + proximoIdEntrenador);
        proximoIdEntrenador++;
    }
    
    public void buscarEntrenador() {
        System.out.print("Introduce el ID del entrenador a buscar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Entrenador buscado = registroHistorico.buscar(new Entrenador(id, "", ""));
        if (buscado != null) {
            System.out.println("Entrenador encontrado: " + buscado);
        } else {
            System.out.println("No se encontró ningún entrenador con ese ID.");
        }
    }

    public void listarEntrenadores() {
        System.out.println("\n--- Registro Histórico de Entrenadores (BST In-Order) ---");
        List<Entrenador> entrenadores = registroHistorico.recorridoInOrder();
        if (entrenadores.isEmpty()) {
            System.out.println("No hay entrenadores registrados.");
        } else {
            entrenadores.forEach(System.out::println);
        }
    }
    
    public void iniciarAventura() {
        System.out.print("Introduce tu ID de Entrenador para jugar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Entrenador entrenadorActual = registroHistorico.buscar(new Entrenador(id, "", ""));

        if (entrenadorActual == null) {
            System.out.println("ID no válido. Registra un entrenador primero.");
            return;
        }
        
        RankingEntrenador rankingActual = rankingLiga.buscar(new RankingEntrenador(id, ""));
        if (rankingActual == null) {
            rankingActual = new RankingEntrenador(id, entrenadorActual.getNombre());
            rankingLiga.insertar(rankingActual);
        }

        System.out.println("\n¡Aventura iniciada para " + entrenadorActual.getNombre() + "!");
        
        boolean enAventura = true;
        while(enAventura) {
            System.out.println("\n--- Menú de Aventura ---");
            System.out.println("1. Explorar hierba alta");
            System.out.println("2. Ir a la Tienda Pokémon");
            System.out.println("3. Ver mi equipo Pokémon");
            System.out.println("4. Volver al menú principal");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    explorarHierbaAlta(entrenadorActual, rankingActual);
                    break;
                case "2":
                    sistemaTienda.abrirTienda(entrenadorActual, scanner);
                    break;
                case "3":
                    System.out.println("--- Tu Equipo ---");
                    entrenadorActual.getEquipo().forEach(System.out::println);
                    System.out.println("PokéDólares: " + entrenadorActual.getPokeDolares());
                    break;
                case "4":
                    enAventura = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
    
    private void explorarHierbaAlta(Entrenador jugador, RankingEntrenador ranking) {
        Random rand = new Random();
        Pokemon salvaje = pokemonSalvajesDisponibles.get(rand.nextInt(pokemonSalvajesDisponibles.size())).crearCopia();
        
        boolean victoria = sistemaBatalla.iniciarBatallaSalvaje(jugador, salvaje, scanner);
        
        if (jugador.getPrimerPokemonVivo() == null) {
            System.out.println("¡Todos tus Pokémon están debilitados! Corre al centro Pokémon (saliendo de la aventura).");
            return;
        }

        if (victoria) {
            ranking.registrarVictoria();
        } else {
            ranking.registrarDerrota();
        }
        rankingLiga.insertar(ranking);
    }
    
    public void mostrarRanking() {
        System.out.println("\n--- Ranking de la Liga (Ordenado por Victorias) ---");
        List<RankingEntrenador> ranking = rankingLiga.recorridoInOrder();
        if (ranking.isEmpty()) {
            System.out.println("El ranking está vacío.");
        } else {
            ranking.sort((r1, r2) -> Integer.compare(r2.getVictorias(), r1.getVictorias()));
            ranking.forEach(System.out::println);
        }
    }
}