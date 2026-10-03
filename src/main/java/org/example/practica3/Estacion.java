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

    // Agrega un dado a la estación
    public void agregarDado(Dado dado) {

        if (cantidadDados < 10) {

            dados[cantidadDados] = dado;

            cantidadDados++;
        }
    }

    // Quita un dado de la estación
    public void quitarDado(Dado dado) {

        for (int i = 0; i < cantidadDados; i++) {

            if (dados[i] == dado) {

                // Recorrer los dados
                // para no dejar espacios vacíos
                for (int j = i;
                     j < cantidadDados - 1;
                     j++) {

                    dados[j] = dados[j + 1];
                }

                dados[cantidadDados - 1] = null;

                cantidadDados--;

                return;
            }
        }
    }

    // Revisa si la estación tiene un dado
    public boolean tieneDado(Dado dado) {

        for (int i = 0; i < cantidadDados; i++) {

            if (dados[i] == dado) {
                return true;
            }
        }

        return false;
    }

    // Lanza todos los dados de la estación
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