package org.example.practica3;

import java.util.Random;

public class Dado {

    private int numero;
    private int valor;
    private Random random;

    public Dado(int numero) {

        this.numero = numero;
        valor = 0;

        // Crear el generador de números aleatorios
        random = new Random();
    }

    public int lanzar() {

        valor = random.nextInt(6) + 1;

        return valor;
    }

    public int getValor() {

        return valor;
    }

    public int getNumero() {

        return numero;
    }
}