package org.example.practica3;

public class Simulacion {

    private Estacion[] estaciones;

    private Dado[] dados;

    private ColaSimple<Cliente>[] colas;

    private int[] fichasNuevas;

    private int siguienteCliente;

    private int ronda;

    private int personasTerminadas;

    private boolean iniciado;

    private boolean tocaMover;

    public Simulacion() {

        estaciones = new Estacion[10];

        dados = new Dado[10];

        // ==================================
        // CREAR ESTACIONES Y DADOS
        // ==================================

        for (int i = 0; i < 10; i++) {

            estaciones[i] =
                    new Estacion(i + 1);

            dados[i] =
                    new Dado(i + 1);

            // Al principio cada estación
            // tiene un dado
            estaciones[i]
                    .agregarDado(dados[i]);
        }

        // ==================================
        // CREAR COLAS
        // ==================================

        colas = new ColaSimple[9];

        for (int i = 0; i < 9; i++) {

            colas[i] =
                    new ColaSimple<>(100);
        }

        fichasNuevas =
                new int[9];

        siguienteCliente = 1;

        ronda = 0;

        personasTerminadas = 0;

        iniciado = false;

        tocaMover = false;
    }

    // ==================================
    // INICIAR JUEGO
    // ==================================

    public void iniciar() {

        if (iniciado) {
            return;
        }

        // 4 personas en cada una
        // de las 9 colas
        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 4; j++) {

                Cliente cliente =
                        new Cliente(
                                siguienteCliente
                        );

                colas[i]
                        .insertarDato(cliente);

                siguienteCliente++;
            }
        }

        for (int i = 0; i < 9; i++) {
            fichasNuevas[i] = 0;
        }

        ronda = 0;

        personasTerminadas = 0;

        iniciado = true;

        tocaMover = false;
    }

    // ==================================
    // LANZAR DADOS
    // ==================================

    public void lanzarDados() {

        if (!iniciado) {
            return;
        }

        if (tocaMover) {
            return;
        }

        if (ronda >= 20) {
            return;
        }

        for (int i = 0; i < 10; i++) {

            estaciones[i]
                    .lanzarDados();
        }

        ronda++;

        // Las fichas azules anteriores
        // dejan de ser nuevas
        for (int i = 0; i < 9; i++) {

            fichasNuevas[i] = 0;
        }

        tocaMover = true;
    }

    // ==================================
    // MOVER DADO
    // ==================================

    public void moverDado(
            int numeroDado,
            int nuevaEstacion) {

        if (numeroDado < 0 ||
                numeroDado >= 10) {

            return;
        }

        if (nuevaEstacion < 0 ||
                nuevaEstacion >= 10) {

            return;
        }

        Dado dado =
                dados[numeroDado];

        // Buscar quién tiene actualmente
        // el dado
        for (int i = 0; i < 10; i++) {

            if (estaciones[i]
                    .tieneDado(dado)) {

                estaciones[i]
                        .quitarDado(dado);

                break;
            }
        }

        // Darlo a la nueva estación
        estaciones[nuevaEstacion]
                .agregarDado(dado);
    }

    // ==================================
    // MOVER PERSONAS
    // ==================================

    public void moverPersonas() {

        if (!iniciado) {
            return;
        }

        if (!tocaMover) {
            return;
        }

        int[] cantidadMover =
                new int[10];

        // ==================================
        // ESTACION 1
        // ==================================

        // La estación 1 recibe personas
        // desde fuera del sistema.
        cantidadMover[0] =
                estaciones[0]
                        .getResultado();

        // ==================================
        // ESTACIONES 2 A 10
        // ==================================

        for (int i = 1;
             i < 10;
             i++) {

            int capacidad =
                    estaciones[i]
                            .getResultado();

            int disponibles =
                    colas[i - 1]
                            .cantidad();

            if (capacidad <
                    disponibles) {

                cantidadMover[i] =
                        capacidad;

            } else {

                cantidadMover[i] =
                        disponibles;
            }
        }

        // ==================================
        // ESTACION 10
        // ==================================

        // Estas personas terminan
        // el proceso
        for (int i = 0;
             i < cantidadMover[9];
             i++) {

            Cliente cliente =
                    colas[8]
                            .eliminarDato();

            if (cliente != null) {

                personasTerminadas++;
            }
        }

        // ==================================
        // ESTACIONES 9 A 2
        // ==================================

        // Se hace de atrás hacia adelante
        // para que nadie avance dos veces
        // en una misma ronda.
        for (int i = 8;
             i >= 1;
             i--) {

            for (int j = 0;
                 j < cantidadMover[i];
                 j++) {

                Cliente cliente =
                        colas[i - 1]
                                .eliminarDato();

                if (cliente != null) {

                    colas[i]
                            .insertarDato(cliente);
                }
            }
        }

        // ==================================
        // ESTACION 1
        // ==================================

        // Agregar personas nuevas
        for (int i = 0;
             i < cantidadMover[0];
             i++) {

            Cliente cliente =
                    new Cliente(
                            siguienteCliente
                    );

            colas[0]
                    .insertarDato(cliente);

            siguienteCliente++;
        }

        // ==================================
        // GUARDAR FICHAS NUEVAS
        // ==================================

        for (int i = 0;
             i < 9;
             i++) {

            fichasNuevas[i] =
                    cantidadMover[i];
        }

        tocaMover = false;
    }

    // ==================================
    // PERSONAS EN EL SISTEMA
    // ==================================

    public int personasEnSistema() {

        int total = 0;

        for (int i = 0;
             i < colas.length;
             i++) {

            total =
                    total
                            + colas[i].cantidad();
        }

        return total;
    }

    // ==================================
    // GETTERS
    // ==================================

    public Estacion[] getEstaciones() {
        return estaciones;
    }

    public Dado[] getDados() {
        return dados;
    }

    public ColaSimple<Cliente>[] getColas() {
        return colas;
    }

    public int[] getFichasNuevas() {
        return fichasNuevas;
    }

    public int getRonda() {
        return ronda;
    }

    public int getPersonasTerminadas() {
        return personasTerminadas;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public boolean isTocaMover() {
        return tocaMover;
    }

    public boolean isFinalizado() {

        return ronda >= 20 &&
                !tocaMover;
    }
}