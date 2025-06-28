package com.pokemon.modelos.entrenador;

import com.pokemon.modelos.objetos.TipoPokeball;
import com.pokemon.modelos.pokemon.Pokemon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entrenador implements Comparable<Entrenador>, Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String nombre;
    private final String correo;
    private final List<Pokemon> equipo;
    private int pokeDolares;
    private final Map<TipoPokeball, Integer> inventarioPokeballs;

    public Entrenador(int id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.equipo = new ArrayList<>();
        this.pokeDolares = 500;
        this.inventarioPokeballs = new HashMap<>();
        this.inventarioPokeballs.put(TipoPokeball.POKEBALL, 5);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Pokemon> getEquipo() { return equipo; }
    public int getPokeDolares() { return pokeDolares; }
    public Map<TipoPokeball, Integer> getInventarioPokeballs() { return inventarioPokeballs; }

    public void ganarDinero(int cantidad) { this.pokeDolares += cantidad; }
    
    public boolean gastarDinero(int cantidad) {
        if (this.pokeDolares >= cantidad) {
            this.pokeDolares -= cantidad;
            return true;
        }
        return false;
    }
    
    public void anadirPokeballs(TipoPokeball tipo, int cantidad) {
        inventarioPokeballs.put(tipo, inventarioPokeballs.getOrDefault(tipo, 0) + cantidad);
    }
    
    public boolean usarPokeball(TipoPokeball tipo) {
        if (inventarioPokeballs.getOrDefault(tipo, 0) > 0) {
            inventarioPokeballs.put(tipo, inventarioPokeballs.get(tipo) - 1);
            return true;
        }
        return false;
    }

    public boolean anadirPokemonAlEquipo(Pokemon pokemon) {
        if (equipo.size() < 6) {
            equipo.add(pokemon);
            System.out.println(pokemon.getNombre() + " fue añadido a tu equipo.");
            return true;
        }
        System.out.println("El equipo está lleno.");
        return false;
    }
    
    public void venderPokemon(int indice) {
        if (equipo.size() <= 1) {
            System.out.println("¡No puedes vender a tu último Pokémon!");
            return;
        }
        if (indice >= 0 && indice < equipo.size()) {
            Pokemon vendido = equipo.remove(indice);
            ganarDinero(vendido.getValorPrecio());
            System.out.println("Has vendido a " + vendido.getNombre() + " por " + vendido.getValorPrecio() + " PokéDólares.");
        }
    }
    
    public Pokemon getPrimerPokemonVivo() {
        for (Pokemon p : equipo) {
            if (p.estaVivo()) {
                return p;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Entrenador otro) {
        return Integer.compare(this.id, otro.id);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Correo: " + correo;
    }
}
