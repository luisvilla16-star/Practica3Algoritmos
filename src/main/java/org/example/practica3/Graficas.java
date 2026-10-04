package org.example.practica3;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Graficas {

    // =====================================
    // GRÁFICAS
    // =====================================

    private BarChart<String, Number> actividad;
    private BarChart<String, Number> throughput;
    private BarChart<String, Number> sistema;
    private BarChart<String, Number> tiempo;

    // =====================================
    // SERIES
    // =====================================

    private XYChart.Series<String, Number> datosThroughput;
    private XYChart.Series<String, Number> datosSistema;
    private XYChart.Series<String, Number> datosTiempo;

    // =====================================
    // ACTIVITY
    // =====================================

    // 10 jugadores y 20 turnos
    private int[][] actividadJugadores = new int[10][20];

    private int ultimoTurno = 0;
    private int jugadorSeleccionado = 0;

    // =====================================
    // TIME IN SYSTEM
    // =====================================

    private int ordenSalida = 0;

    // =====================================
    // VENTANAS
    // =====================================

    private Stage ventanaActividad;
    private Stage ventanaThroughput;
    private Stage ventanaSistema;
    private Stage ventanaTiempo;

    // Activity necesita gráfica + botones
    private VBox contenidoActividad;


    // =====================================
    // CONSTRUCTOR
    // =====================================

    public Graficas() {

        // Crear las cuatro gráficas

        actividad = crearGrafica(
                "Activity - Player 1",
                "Turn",
                "Activity"
        );

        throughput = crearGrafica(
                "Throughput",
                "Turn",
                "Throughput"
        );

        sistema = crearGrafica(
                "Number in system",
                "Turn",
                "Number in system"
        );

        tiempo = crearGrafica(
                "Time in system",
                "Order of arrival",
                "Time in system"
        );


        // Crear series

        datosThroughput =
                new XYChart.Series<>();

        datosSistema =
                new XYChart.Series<>();

        datosTiempo =
                new XYChart.Series<>();


        // Colocar series en gráficas

        throughput.getData()
                .add(datosThroughput);

        sistema.getData()
                .add(datosSistema);

        tiempo.getData()
                .add(datosTiempo);


        // El sistema inicia con 36 personas

        datosSistema.getData().add(
                new XYChart.Data<>(
                        "0",
                        36
                )
        );


        // Crear botones de Activity

        crearBotonesActividad();
    }


    // =====================================
    // CREAR UNA GRÁFICA
    // =====================================

    private BarChart<String, Number> crearGrafica(
            String titulo,
            String nombreX,
            String nombreY) {

        CategoryAxis ejeX =
                new CategoryAxis();

        NumberAxis ejeY =
                new NumberAxis();

        ejeX.setLabel(nombreX);
        ejeY.setLabel(nombreY);

        BarChart<String, Number> grafica =
                new BarChart<>(
                        ejeX,
                        ejeY
                );

        grafica.setTitle(titulo);

        grafica.setAnimated(false);

        grafica.setLegendVisible(false);

        return grafica;
    }


    // =====================================
    // CREAR BOTONES DE ACTIVITY
    // =====================================

    private void crearBotonesActividad() {

        HBox botones =
                new HBox(5);

        botones.setAlignment(
                Pos.CENTER
        );

        Label texto =
                new Label("Player:");

        botones.getChildren()
                .add(texto);


        // Botones del 1 al 10

        for (int i = 0; i < 10; i++) {

            int jugador = i;

            Button boton =
                    new Button(
                            String.valueOf(i + 1)
                    );

            boton.setOnAction(e ->
                    mostrarJugador(jugador)
            );

            botones.getChildren()
                    .add(boton);
        }


        // Botón ALL

        Button botonTodos =
                new Button("ALL");

        botonTodos.setOnAction(e ->
                mostrarTodos()
        );

        botones.getChildren()
                .add(botonTodos);


        // Activity + botones

        contenidoActividad =
                new VBox(
                        10,
                        actividad,
                        botones
                );

        contenidoActividad.setAlignment(
                Pos.CENTER
        );
    }


    // =====================================
    // MOSTRAR UN JUGADOR
    // =====================================

    private void mostrarJugador(
            int jugador) {

        jugadorSeleccionado =
                jugador;

        // Limpiar gráfica

        actividad.getData()
                .clear();

        actividad.setLegendVisible(false);

        actividad.setTitle(
                "Activity - Player "
                        + (jugador + 1)
        );


        // Crear datos

        XYChart.Series<String, Number> datos =
                new XYChart.Series<>();


        // Agregar los turnos

        for (int turno = 0;
             turno < ultimoTurno;
             turno++) {

            datos.getData().add(

                    new XYChart.Data<>(

                            String.valueOf(
                                    turno + 1
                            ),

                            actividadJugadores
                                    [jugador][turno]
                    )
            );
        }


        actividad.getData()
                .add(datos);
    }


    // =====================================
    // MOSTRAR TODOS
    // =====================================

    private void mostrarTodos() {

        actividad.getData()
                .clear();

        actividad.setTitle(
                "Activity - ALL"
        );

        actividad.setLegendVisible(true);


        // Crear una serie
        // para cada jugador

        for (int jugador = 0;
             jugador < 10;
             jugador++) {

            XYChart.Series<String, Number> datos =
                    new XYChart.Series<>();

            datos.setName(
                    "Player "
                            + (jugador + 1)
            );


            // Agregar sus turnos

            for (int turno = 0;
                 turno < ultimoTurno;
                 turno++) {

                datos.getData().add(

                        new XYChart.Data<>(

                                String.valueOf(
                                        turno + 1
                                ),

                                actividadJugadores
                                        [jugador][turno]
                        )
                );
            }


            actividad.getData()
                    .add(datos);
        }
    }


    // =====================================
    // ACTUALIZAR GRÁFICAS
    // =====================================

    public void actualizar(
            int turno,
            Simulacion simulacion) {

        // Solo existen 20 turnos

        if (turno < 1 ||
                turno > 20) {

            return;
        }


        ultimoTurno = turno;


        // =================================
        // ACTIVITY
        // =================================

        int[] movimientos =
                simulacion
                        .getUltimoMovimiento();


        // Guardar actividad
        // de los 10 jugadores

        for (int i = 0;
             i < 10;
             i++) {

            actividadJugadores
                    [i][turno - 1] =
                    movimientos[i];
        }


        // Actualizar el jugador
        // seleccionado

        mostrarJugador(
                jugadorSeleccionado
        );


        // =================================
        // THROUGHPUT
        // =================================

        agregarDato(
                datosThroughput,

                turno,

                simulacion
                        .getSalidasUltimoTurno()
        );


        // =================================
        // NUMBER IN SYSTEM
        // =================================

        agregarDato(
                datosSistema,

                turno,

                simulacion
                        .personasEnSistema()
        );
    }


    // =====================================
    // AGREGAR DATO A UNA GRÁFICA
    // =====================================

    private void agregarDato(
            XYChart.Series<String, Number> serie,
            int numero,
            int valor) {

        serie.getData().add(

                new XYChart.Data<>(

                        String.valueOf(numero),

                        valor
                )
        );
    }


    // =====================================
    // TIME IN SYSTEM
    // =====================================

    public void agregarTiempoPersona(
            int numeroPersona,
            int tiempoPersona) {

        ordenSalida++;

        agregarDato(
                datosTiempo,
                ordenSalida,
                tiempoPersona
        );
    }


    // =====================================
    // MOSTRAR ACTIVITY
    // =====================================

    public void mostrarActividad() {

        if (ventanaActividad == null) {

            ventanaActividad =
                    new Stage();

            ventanaActividad.setTitle(
                    "Activity"
            );

            ventanaActividad.setScene(

                    new Scene(
                            contenidoActividad,
                            1000,
                            650
                    )
            );
        }


        ventanaActividad.show();

        ventanaActividad.toFront();
    }


    // =====================================
    // MOSTRAR THROUGHPUT
    // =====================================

    public void mostrarThroughput() {

        if (ventanaThroughput == null) {

            ventanaThroughput =
                    crearVentana(
                            throughput,
                            "Throughput"
                    );
        }

        ventanaThroughput.show();

        ventanaThroughput.toFront();
    }


    // =====================================
    // MOSTRAR NUMBER IN SYSTEM
    // =====================================

    public void mostrarSistema() {

        if (ventanaSistema == null) {

            ventanaSistema =
                    crearVentana(
                            sistema,
                            "Number in system"
                    );
        }

        ventanaSistema.show();

        ventanaSistema.toFront();
    }


    // =====================================
    // MOSTRAR TIME IN SYSTEM
    // =====================================

    public void mostrarTiempo() {

        if (ventanaTiempo == null) {

            ventanaTiempo =
                    crearVentana(
                            tiempo,
                            "Time in system"
                    );
        }

        ventanaTiempo.show();

        ventanaTiempo.toFront();
    }


    // =====================================
    // CREAR VENTANA
    // =====================================

    private Stage crearVentana(
            BarChart<String, Number> grafica,
            String titulo) {

        Stage ventana =
                new Stage();

        ventana.setTitle(titulo);

        ventana.setScene(

                new Scene(
                        grafica,
                        1000,
                        650
                )
        );

        return ventana;
    }
}