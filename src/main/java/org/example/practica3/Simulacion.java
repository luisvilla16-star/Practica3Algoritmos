package org.example.practica3;

public class Simulacion {

    private Estacion[] estaciones;
    private Dado[] dados;
    private ColaSimple<Cliente>[] colas;

    private int[] fichasNuevas;
    private int[] ultimoMovimiento;

    private int siguienteCliente;
    private int ronda;
    private int personasTerminadas;

    private int salidasUltimoTurno;

    private int[] personasSalieron;
    private int[] tiemposPersonas;
    private int cantidadSalieron;

    private boolean iniciado;
    private boolean tocaMover;

    public Simulacion() {

        estaciones = new Estacion[10];
        dados = new Dado[10];

        // Crear las 10 estaciones
        // y colocar un dado en cada una
        for (int i = 0; i < 10; i++) {

            estaciones[i] =
                    new Estacion(i + 1);

            dados[i] =
                    new Dado(i + 1);

            estaciones[i]
                    .agregarDado(dados[i]);
        }

        // Crear las 9 colas
        colas = new ColaSimple[9];

        for (int i = 0; i < 9; i++) {

            colas[i] =
                    new ColaSimple<>(100);
        }

        fichasNuevas = new int[9];

        // Guarda cuánto movió
        // cada estación
        ultimoMovimiento =
                new int[10];

        personasSalieron =
                new int[100];

        tiemposPersonas =
                new int[100];

        siguienteCliente = 1;

        ronda = 0;
        personasTerminadas = 0;

        salidasUltimoTurno = 0;
        cantidadSalieron = 0;

        iniciado = false;
        tocaMover = false;
    }

    // =====================================
    // INICIAR
    // =====================================

    public void iniciar() {

        if (iniciado) {
            return;
        }

        // 4 personas en cada una
        // de las 9 colas
        // 9 x 4 = 36

        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 4; j++) {

                Cliente cliente =
                        new Cliente(
                                siguienteCliente,
                                0
                        );

                colas[i]
                        .insertarDato(cliente);

                siguienteCliente++;
            }
        }

        iniciado = true;
        tocaMover = false;
    }

    // =====================================
    // LANZAR DADOS
    // =====================================

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
        // pasan a ser grises
        for (int i = 0; i < 9; i++) {

            fichasNuevas[i] = 0;
        }

        tocaMover = true;
    }

    // =====================================
    // MOVER DADO
    // =====================================

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

        // Quitar de estación anterior
        for (int i = 0; i < 10; i++) {

            if (estaciones[i]
                    .tieneDado(dado)) {

                estaciones[i]
                        .quitarDado(dado);

                break;
            }
        }

        // Agregar a estación nueva
        estaciones[nuevaEstacion]
                .agregarDado(dado);
    }

    // =====================================
    // MOVER PERSONAS
    // =====================================

    public void moverPersonas() {

        if (!iniciado ||
                !tocaMover) {

            return;
        }

        salidasUltimoTurno = 0;
        cantidadSalieron = 0;

        int[] movimientos =
                calcularMovimientos();

        // Guardar actividad de
        // cada jugador
        for (int i = 0; i < 10; i++) {

            ultimoMovimiento[i] =
                    movimientos[i];
        }

        sacarTerminados(
                movimientos[9]
        );

        moverEntreEstaciones(
                movimientos
        );

        crearPersonas(
                movimientos[0]
        );

        // Guardar fichas que
        // acaban de llegar
        for (int i = 0; i < 9; i++) {

            fichasNuevas[i] =
                    movimientos[i];
        }

        tocaMover = false;
    }

    // =====================================
    // CALCULAR CUÁNTOS PUEDEN MOVERSE
    // =====================================

    private int[] calcularMovimientos() {

        int[] movimientos =
                new int[10];

        // Estación 1:
        // entrada ilimitada
        movimientos[0] =
                estaciones[0]
                        .getResultado();

        // Estaciones 2 a 10
        for (int i = 1; i < 10; i++) {

            int capacidad =
                    estaciones[i]
                            .getResultado();

            int disponibles =
                    colas[i - 1]
                            .cantidad();

            if (capacidad < disponibles) {

                movimientos[i] =
                        capacidad;

            } else {

                movimientos[i] =
                        disponibles;
            }
        }

        return movimientos;
    }

    // =====================================
    // SACAR TERMINADOS
    // =====================================

    private void sacarTerminados(
            int cantidad) {

        for (int i = 0;
             i < cantidad;
             i++) {

            Cliente cliente =
                    colas[8]
                            .eliminarDato();

            if (cliente != null) {

                personasTerminadas++;
                salidasUltimoTurno++;

                int tiempo =
                        ronda
                                - cliente.getTurnoEntrada();

                personasSalieron[
                        cantidadSalieron
                        ] = cliente.getNumero();

                tiemposPersonas[
                        cantidadSalieron
                        ] = tiempo;

                cantidadSalieron++;
            }
        }
    }

    // =====================================
    // MOVER ENTRE ESTACIONES
    // =====================================

    private void moverEntreEstaciones(
            int[] movimientos) {

        /*
         * Se mueve de atrás hacia adelante
         * para evitar que una persona
         * avance dos veces en el mismo turno.
         */

        for (int i = 8; i >= 1; i--) {

            for (int j = 0;
                 j < movimientos[i];
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
    }

    // =====================================
    // PERSONAS NUEVAS
    // =====================================

    private void crearPersonas(
            int cantidad) {

        for (int i = 0;
             i < cantidad;
             i++) {

            Cliente cliente =
                    new Cliente(
                            siguienteCliente,
                            ronda
                    );

            colas[0]
                    .insertarDato(cliente);

            siguienteCliente++;
        }
    }

    // =====================================
    // PERSONAS EN SISTEMA
    // =====================================

    public int personasEnSistema() {

        int total = 0;

        for (int i = 0; i < 9; i++) {

            total +=
                    colas[i].cantidad();
        }

        return total;
    }

    // =====================================
    // GETTERS
    // =====================================

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

    public int[] getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public int getRonda() {
        return ronda;
    }

    public int getPersonasTerminadas() {
        return personasTerminadas;
    }

    public int getSalidasUltimoTurno() {
        return salidasUltimoTurno;
    }

    public int[] getPersonasSalieron() {
        return personasSalieron;
    }

    public int[] getTiemposPersonas() {
        return tiemposPersonas;
    }

    public int getCantidadSalieron() {
        return cantidadSalieron;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public boolean isTocaMover() {
        return tocaMover;
    }

    public boolean isFinalizado() {

        return ronda >= 20
                && !tocaMover;
    }
}