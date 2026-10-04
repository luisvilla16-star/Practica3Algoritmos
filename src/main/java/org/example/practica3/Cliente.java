package org.example.practica3;

public class Cliente {

    private int numero;
    private int turnoEntrada;

    public Cliente(
            int numero,
            int turnoEntrada) {

        this.numero = numero;
        this.turnoEntrada = turnoEntrada;
    }

    public int getNumero() {
        return numero;
    }

    public int getTurnoEntrada() {
        return turnoEntrada;
    }

    @Override
    public String toString() {

        return "Cliente " + numero;
    }
}