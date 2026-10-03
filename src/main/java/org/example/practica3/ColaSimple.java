package org.example.practica3;

public class ColaSimple<T> {

    private T[] cola;
    private int fin;
    private int inicio;


    // Constructor con capacidad de 10
    public ColaSimple() {
        cola = (T[]) new Object[10];
        inicio = -1;
        fin = -1;
    }


    // Constructor indicando la capacidad
    public ColaSimple(int cantidad) {
        cola = (T[]) new Object[cantidad];
        inicio = -1;
        fin = -1;
    }


    // Insertar un dato al final de la cola
    public boolean insertarDato(T dato) {

        if (fin < cola.length - 1) {

            fin = fin + 1;
            cola[fin] = dato;

            if (inicio == -1) {
                inicio = 0;
            }

            return true;

        } else {

            return false;
        }
    }


    // Eliminar el dato que está al inicio
    public T eliminarDato() {

        if (inicio != -1) {

            T dato = cola[inicio];

            // Opcionalmente limpiamos la posición
            cola[inicio] = null;

            if (inicio == fin) {

                inicio = -1;
                fin = -1;

            } else {

                inicio = inicio + 1;
            }

            return dato;

        } else {

            System.out.println("Subdesbordamiento");
            return null;
        }
    }



    public int cantidad() {

        if (inicio == -1) {
            return 0;
        }

        return fin - inicio + 1;
    }
}