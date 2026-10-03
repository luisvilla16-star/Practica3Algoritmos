package org.example.practica3;

public class Cliente {

    private int numero;

    public Cliente(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Cliente " + numero;
    }
}