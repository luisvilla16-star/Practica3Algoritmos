package org.example.practica3;

import java.util.Random;

public class Dado {

    private int numero;
    private int valor;
    private Random random;

    public Dado(int numero) {

        this.numero = numero;
        valor = 0;

        random = new Random();
    }

    public void lanzar() {

        valor =
                random.nextInt(6) + 1;
    }

    public int getNumero() {
        return numero;
    }

    public int getValor() {
        return valor;
    }
}