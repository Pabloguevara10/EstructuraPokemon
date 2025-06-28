package com.pokemon.modelos.pokemon;

public class Pokemon {

    private final String nombre;
    private final TipoPokemon tipo;
    private int psMax;
    private int psActual;
    private int ataque;
    private int defensa;
    private int nivel;
    private int experiencia;
    private int experienciaParaSiguienteNivel;
    private int valorPrecio;

    public Pokemon(String nombre, TipoPokemon tipo, int psMax, int ataque, int defensa, int nivel) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.psMax = psMax;
        this.psActual = psMax;
        this.ataque = ataque;
        this.defensa = defensa;
        this.nivel = nivel;
        this.experiencia = 0;
        this.valorPrecio = nivel * 10;
        actualizarExperienciaRequerida();
    }

    public String getNombre() { return nombre; }
    public TipoPokemon getTipo() { return tipo; }
    public int getPsMax() { return psMax; }
    public int getPsActual() { return psActual; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getNivel() { return nivel; }
    public int getValorPrecio() { return valorPrecio; }
    
    public void setPsActual(int psActual) {
        this.psActual = Math.max(0, Math.min(psMax, psActual));
    }

    private void actualizarExperienciaRequerida() {
        this.experienciaParaSiguienteNivel = (int) (Math.pow(nivel, 2) * 10);
    }
    
    public void ganarExperiencia(int xpGanada) {
        this.experiencia += xpGanada;
        System.out.println(this.nombre + " ha ganado " + xpGanada + " puntos de experiencia.");
        
        while (this.experiencia >= this.experienciaParaSiguienteNivel) {
            subirDeNivel();
        }
    }

    private void subirDeNivel() {
        this.experiencia -= this.experienciaParaSiguienteNivel;
        this.nivel++;
        
        this.psMax += 5;
        this.psActual = this.psMax;
        this.ataque += 5;
        this.defensa += 5;
        this.valorPrecio += 20;
        
        System.out.println("------------------------------------------");
        System.out.println("¡" + this.nombre + " ha subido al nivel " + this.nivel + "!");
        System.out.println("PS: " + (psMax - 5) + " -> " + psMax);
        System.out.println("Ataque: " + (ataque - 5) + " -> " + ataque);
        System.out.println("Defensa: " + (defensa - 5) + " -> " + defensa);
        System.out.println("------------------------------------------");
        
        actualizarExperienciaRequerida();
    }
    
    public boolean estaVivo() { return this.psActual > 0; }

    public Pokemon crearCopia() {
        return new Pokemon(this.nombre, this.tipo, this.psMax, this.ataque, this.defensa, this.nivel);
    }
    
    @Override
    public String toString() {
        return nombre + " (Nvl " + nivel + ") | " + tipo + " | " + psActual + "/" + psMax + " PS | XP: " + experiencia + "/" + experienciaParaSiguienteNivel;
    }
}