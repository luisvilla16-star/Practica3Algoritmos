package org.example.practica3;

public class Estacion {

    private int numero;

    private Dado[] dados;

    private int cantidadDados;
    private int resultado;

    public Estacion(int numero) {

        this.numero = numero;

        dados = new Dado[10];

        cantidadDados = 0;
        resultado = 0;
    }

    public void agregarDado(Dado dado) {

        if (cantidadDados < 10) {

            dados[cantidadDados] = dado;

            cantidadDados++;
        }
    }

    public void quitarDado(Dado dado) {

        for (int i = 0;
             i < cantidadDados;
             i++) {

            if (dados[i] == dado) {

                for (int j = i;
                     j < cantidadDados - 1;
                     j++) {

                    dados[j] =
                            dados[j + 1];
                }

                dados[cantidadDados - 1] =
                        null;

                cantidadDados--;

                return;
            }
        }
    }

    public boolean tieneDado(Dado dado) {

        for (int i = 0;
             i < cantidadDados;
             i++) {

            if (dados[i] == dado) {
                return true;
            }
        }

        return false;
    }

    public void lanzarDados() {

        resultado = 0;

        for (int i = 0;
             i < cantidadDados;
             i++) {

            dados[i].lanzar();

            resultado =
                    resultado
                            + dados[i].getValor();
        }
    }

    public int getNumero() {
        return numero;
    }

    public int getCantidadDados() {
        return cantidadDados;
    }

    public int getResultado() {
        return resultado;
    }
}