package org.example.practica3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HelloController {

    private Simulacion simulacion;
    private Tablero tablero;
    private Graficas graficas;

    private Label lblRonda;
    private Label lblTerminados;
    private Label lblSistema;

    private Button btnAccion;

    public HelloController() {

        simulacion =
                new Simulacion();

        tablero =
                new Tablero(simulacion);

        graficas =
                new Graficas();
    }

    // =====================================
    // CREAR ESCENA
    // =====================================

    public Scene crearEscena() {

        BorderPane ventana =
                new BorderPane();

        Label titulo =
                new Label(
                        "THE DICE GAME 2"
                );

        titulo.setStyle(
                "-fx-font-size: 22;"
                        + "-fx-font-weight: bold;"
        );

        BorderPane.setAlignment(
                titulo,
                Pos.CENTER
        );

        BorderPane.setMargin(
                titulo,
                new Insets(10)
        );

        ventana.setTop(titulo);

        ventana.setCenter(
                tablero.crearTablero()
        );

        ventana.setRight(
                crearPanel()
        );

        return new Scene(ventana);
    }

    // =====================================
    // PANEL DERECHO
    // =====================================

    private VBox crearPanel() {

        Label titulo =
                new Label("RESULTADOS");

        titulo.setStyle(
                "-fx-font-size: 18;"
                        + "-fx-font-weight: bold;"
        );

        Button btnActividad =
                new Button("ACTIVIDAD");

        Button btnThroughput =
                new Button("RENDIMIENTO");

        Button btnSistema =
                new Button(
                        "PERSONAS EN EL SISTEMA"
                );

        Button btnTiempo =
                new Button(
                        "TIEMPO EN EL SISTEMA"
                );

        btnActividad.setOnAction(
                e -> graficas
                        .mostrarActividad()
        );

        btnThroughput.setOnAction(
                e -> graficas
                        .mostrarThroughput()
        );

        btnSistema.setOnAction(
                e -> graficas
                        .mostrarSistema()
        );

        btnTiempo.setOnAction(
                e -> graficas
                        .mostrarTiempo()
        );

        lblRonda =
                new Label(
                        "Turno: 0 / 20"
                );

        lblTerminados =
                new Label(
                        "Terminados: 0"
                );

        lblSistema =
                new Label(
                        "Personas en el sistema: 0"
                );

        btnAccion =
                new Button("INICIAR");

        btnAccion.setPrefWidth(180);

        btnAccion.setOnAction(
                e -> accionBoton()
        );

        VBox panel =
                new VBox(
                        15,
                        titulo,
                        btnActividad,
                        btnThroughput,
                        btnSistema,
                        btnTiempo,
                        lblRonda,
                        lblTerminados,
                        lblSistema,
                        btnAccion
                );

        panel.setPadding(
                new Insets(20)
        );

        panel.setAlignment(
                Pos.CENTER
        );

        panel.setPrefWidth(230);

        return panel;
    }

    // =====================================
    // BOTÓN
    // =====================================

    private void accionBoton() {

        // =================================
        // INICIAR
        // =================================

        if (!simulacion.isIniciado()) {

            simulacion.iniciar();

            tablero.actualizarColas();

            tablero.limpiarDados();

            actualizarDatos();

            btnAccion.setText(
                    "LANZAR"
            );

            return;
        }

        // =================================
        // LANZAR
        // =================================

        if (!simulacion.isTocaMover()) {

            simulacion.lanzarDados();

            tablero.mostrarDados();

            /*
             * Al lanzar:
             * las fichas azules anteriores
             * pasan visualmente a gris.
             */

            tablero.actualizarColas();

            actualizarDatos();

            btnAccion.setText(
                    "MOVER"
            );

            return;
        }

        // =================================
        // MOVER
        // =================================

        simulacion.moverPersonas();

        tablero.actualizarColas();

        actualizarDatos();

        /*
         * MUY IMPORTANTE:
         *
         * Siempre se actualizan las gráficas
         * después de MOVER.
         *
         * No importa si las ventanas de las
         * gráficas están abiertas o cerradas.
         */

        actualizarGraficas();

        // =================================
        // FINAL
        // =================================

        if (simulacion.isFinalizado()) {

            btnAccion.setText(
                    "FINALIZADO"
            );

            btnAccion.setDisable(true);

        } else {

            btnAccion.setText(
                    "LANZAR"
            );
        }
    }

    // =====================================
    // ACTUALIZAR ETIQUETAS
    // =====================================

    private void actualizarDatos() {

        lblRonda.setText(
                "Turno: "
                        + simulacion.getRonda()
                        + " / 20"
        );

        lblTerminados.setText(
                "Terminados: "
                        + simulacion
                        .getPersonasTerminadas()
        );

        lblSistema.setText(
                "Personas en el sistema: "
                        + simulacion
                        .personasEnSistema()
        );
    }

    // =====================================
    // ACTUALIZAR GRÁFICAS
    // =====================================

    private void actualizarGraficas() {

        // Activity
        // Throughput
        // Number in system

        graficas.actualizar(
                simulacion.getRonda(),
                simulacion
        );

        // -----------------------------
        // Time in system
        // -----------------------------

        int[] personas =
                simulacion
                        .getPersonasSalieron();

        int[] tiempos =
                simulacion
                        .getTiemposPersonas();

        int cantidad =
                simulacion
                        .getCantidadSalieron();

        for (int i = 0;
             i < cantidad;
             i++) {

            graficas.agregarTiempoPersona(
                    personas[i],
                    tiempos[i]
            );
        }
    }
}