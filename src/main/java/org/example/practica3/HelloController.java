package org.example.practica3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class HelloController {

    // =====================================
    // SIMULACION
    // =====================================

    private Simulacion simulacion;


    // =====================================
    // DADOS DE LA PANTALLA
    // =====================================

    private Label[] dados;

    private FlowPane[] espaciosDados;


    // =====================================
    // COLAS DE PERSONAS
    // =====================================

    private FlowPane[] filasPersonas;


    // =====================================
    // PANEL DERECHO
    // =====================================

    private Label lblRonda;

    private Label lblTerminados;

    private Label lblSistema;

    private Button btnAccion;


    // =====================================
    // DADO SELECCIONADO
    // =====================================

    private int dadoSeleccionado;


    // =====================================
    // CONSTRUCTOR
    // =====================================

    public HelloController() {

        simulacion = new Simulacion();

        dados = new Label[10];

        espaciosDados = new FlowPane[10];

        filasPersonas = new FlowPane[9];

        dadoSeleccionado = -1;
    }


    // =====================================
    // CREAR ESCENA
    // =====================================

    public Scene crearEscena() {

        BorderPane ventana =
                new BorderPane();


        ventana.setStyle(
                "-fx-background-color: #eee9df;"
        );


        // =================================
        // TITULO
        // =================================

        Label titulo =
                new Label(
                        "THE DICE GAME"
                );


        titulo.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8;"
        );


        BorderPane.setAlignment(
                titulo,
                Pos.CENTER
        );


        ventana.setTop(
                titulo
        );


        // =================================
        // TABLERO
        // =================================

        ventana.setCenter(
                crearTablero()
        );


        // =================================
        // PANEL DERECHO
        // =================================

        ventana.setRight(
                crearPanel()
        );


        // =================================
        // EL TAMAÑO DE LA VENTANA
        // LO CONTROLA HELLOAPPLICATION
        // =================================

        return new Scene(
                ventana
        );
    }


    // =====================================
    // CREAR TABLERO
    // =====================================

    private GridPane crearTablero() {

        GridPane tablero =
                new GridPane();


        tablero.setAlignment(
                Pos.CENTER
        );


        tablero.setPadding(
                new Insets(5)
        );


        tablero.setHgap(4);

        tablero.setVgap(4);


        tablero.setStyle(
                "-fx-background-color: #f4f0e8;"
        );


        // =================================
        // CREAR LAS 10 ESTACIONES
        // =================================

        for (int i = 0;
             i < 10;
             i++) {


            VBox estacion =
                    crearEstacion(i);


            int columna = 0;

            int fila = 0;


            // =================================
            // ESTACIONES 1, 2, 3 Y 4
            // =================================

            if (i <= 3) {

                columna = i;

                fila = 0;
            }


            // =================================
            // ESTACION 5
            // =================================

            if (i == 4) {

                columna = 3;

                fila = 1;
            }


            // =================================
            // ESTACION 6
            // =================================

            if (i == 5) {

                columna = 3;

                fila = 2;
            }


            // =================================
            // ESTACIONES 7, 8, 9 Y 10
            // =================================

            if (i >= 6) {

                columna =
                        9 - i;

                fila = 3;
            }


            tablero.add(
                    estacion,
                    columna,
                    fila
            );
        }


        return tablero;
    }


    // =====================================
    // CREAR ESTACION
    // =====================================

    private VBox crearEstacion(
            int numero) {


        VBox estacion =
                new VBox(3);


        estacion.setAlignment(
                Pos.CENTER
        );


        // =================================
        // TAMAÑO DE CADA ESTACION
        // =================================

        estacion.setMinSize(
                200,
                145
        );


        estacion.setPrefSize(
                200,
                145
        );


        estacion.setMaxSize(
                200,
                145
        );


        estacion.setStyle(
                "-fx-border-color: #555555;" +
                        "-fx-border-width: 1;"
        );


        // =================================
        // NUMERO DE ESTACION
        // =================================

        Label lblEstacion =
                new Label(
                        "ESTACIÓN "
                                + (numero + 1)
                );


        lblEstacion.setStyle(
                "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;"
        );


        // =================================
        // IMAGEN DE PERSONA
        // =================================

        ImageView persona =
                crearImagenPersona(
                        numero
                );


        persona.setOnMouseClicked(
                event -> {

                    seleccionarPersona(
                            numero
                    );

                    event.consume();
                }
        );


        estacion.setOnMouseClicked(
                event -> {

                    seleccionarPersona(
                            numero
                    );
                }
        );


        // =================================
        // ESPACIO PARA DADOS
        // =================================

        espaciosDados[numero] =
                new FlowPane();


        espaciosDados[numero]
                .setAlignment(
                        Pos.CENTER
                );


        espaciosDados[numero]
                .setHgap(3);


        espaciosDados[numero]
                .setVgap(3);


        espaciosDados[numero]
                .setPrefWrapLength(
                        190
                );


        espaciosDados[numero]
                .setPrefWidth(
                        190
                );


        espaciosDados[numero]
                .setMinHeight(
                        35
                );


        espaciosDados[numero]
                .setPrefHeight(
                        35
                );


        // =================================
        // DADO INICIAL
        // =================================

        Label dado =
                crearDado(
                        numero
                );


        dados[numero] =
                dado;


        espaciosDados[numero]
                .getChildren()
                .add(
                        dado
                );


        // =================================
        // ESTACIONES 1 A 9
        // =================================

        if (numero < 9) {


            // =================================
            // ESPACIO PARA LAS FICHAS
            // =================================

            filasPersonas[numero] =
                    new FlowPane();


            filasPersonas[numero]
                    .setAlignment(
                            Pos.CENTER
                    );


            filasPersonas[numero]
                    .setHgap(
                            2
                    );


            filasPersonas[numero]
                    .setVgap(
                            2
                    );


            // =================================
            // LAS FICHAS BAJAN A OTRA FILA
            // CUANDO YA NO CABEN
            // =================================

            filasPersonas[numero]
                    .setPrefWrapLength(
                            170
                    );


            filasPersonas[numero]
                    .setPrefWidth(
                            170
                    );


            // =================================
            // ESPACIO PARA LAS FICHAS
            // =================================

            filasPersonas[numero]
                    .setMinHeight(
                            25
                    );


            filasPersonas[numero]
                    .setPrefHeight(
                            30
                    );


            filasPersonas[numero]
                    .setMaxHeight(
                            30
                    );


            // =================================
            // AGREGAR ELEMENTOS
            //
            // Ya NO aparece "Personas: X"
            // =================================

            estacion.getChildren()
                    .addAll(
                            lblEstacion,
                            persona,
                            espaciosDados[numero],
                            filasPersonas[numero]
                    );


        } else {


            // =================================
            // ESTACION 10
            // =================================

            Label salida =
                    new Label(
                            "SALIDA"
                    );


            salida.setStyle(
                    "-fx-font-weight: bold;"
            );


            estacion.getChildren()
                    .addAll(
                            lblEstacion,
                            persona,
                            espaciosDados[numero],
                            salida
                    );
        }


        return estacion;
    }


    // =====================================
    // CREAR IMAGEN DE PERSONA
    // =====================================

    private ImageView crearImagenPersona(
            int numero) {


        ImageView persona =
                new ImageView();


        try {


            Image imagen =
                    new Image(

                            getClass()
                                    .getResource(

                                            "/org/example/practica3/IMAGENES/persona"

                                                    + (numero + 1)

                                                    + ".jpg"
                                    )
                                    .toExternalForm()
                    );


            persona.setImage(
                    imagen
            );


        } catch (Exception e) {


            // Si no existe la imagen
            // el programa continúa.

        }


        persona.setFitWidth(
                55
        );


        persona.setFitHeight(
                55
        );


        persona.setPreserveRatio(
                true
        );


        return persona;
    }


    // =====================================
    // CREAR DADO
    // =====================================

    private Label crearDado(
            int numero) {


        Label dado =
                new Label(
                        "-"
                );


        dado.setAlignment(
                Pos.CENTER
        );


        // =================================
        // TAMAÑO DEL DADO
        // =================================

        dado.setMinSize(
                32,
                32
        );


        dado.setPrefSize(
                32,
                32
        );


        dado.setMaxSize(
                32,
                32
        );


        ponerDadoRojo(
                dado
        );


        dado.setOnMouseClicked(
                event -> {

                    seleccionarDado(
                            numero
                    );

                    event.consume();
                }
        );


        return dado;
    }


    // =====================================
    // SELECCIONAR DADO
    // =====================================

    private void seleccionarDado(
            int numero) {


        // Todos los dados regresan a rojo.

        for (int i = 0;
             i < 10;
             i++) {


            ponerDadoRojo(
                    dados[i]
            );
        }


        dadoSeleccionado =
                numero;


        // Dado seleccionado en naranja.

        dados[numero]
                .setStyle(

                        "-fx-background-color: orange;" +

                                "-fx-text-fill: white;" +

                                "-fx-font-size: 15px;" +

                                "-fx-font-weight: bold;" +

                                "-fx-background-radius: 6;"
                );
    }


    // =====================================
    // SELECCIONAR PERSONA
    // =====================================

    private void seleccionarPersona(
            int numeroPersona) {


        if (dadoSeleccionado == -1) {

            return;
        }


        // =================================
        // MOVER DADO EN LA LOGICA
        // =================================

        simulacion.moverDado(
                dadoSeleccionado,
                numeroPersona
        );


        // =================================
        // MOVER DADO VISUALMENTE
        // =================================

        moverDadoVisual(
                dadoSeleccionado,
                numeroPersona
        );


        ponerDadoRojo(
                dados[dadoSeleccionado]
        );


        dadoSeleccionado = -1;
    }


    // =====================================
    // MOVER DADO VISUAL
    // =====================================

    private void moverDadoVisual(
            int numeroDado,
            int nuevaEstacion) {


        Label dado =
                dados[numeroDado];


        // =================================
        // QUITAR DADO DE LA ESTACION ACTUAL
        // =================================

        for (int i = 0;
             i < 10;
             i++) {


            espaciosDados[i]
                    .getChildren()
                    .remove(
                            dado
                    );
        }


        // =================================
        // COLOCAR EN NUEVA ESTACION
        // =================================

        espaciosDados[nuevaEstacion]
                .getChildren()
                .add(
                        dado
                );
    }


    // =====================================
    // COLOR NORMAL DEL DADO
    // =====================================

    private void ponerDadoRojo(
            Label dado) {


        dado.setStyle(

                "-fx-background-color: #c62828;" +

                        "-fx-text-fill: white;" +

                        "-fx-font-size: 15px;" +

                        "-fx-font-weight: bold;" +

                        "-fx-background-radius: 6;"
        );
    }


    // =====================================
    // CREAR PANEL DERECHO
    // =====================================

    private VBox crearPanel() {


        VBox panel =
                new VBox(
                        12
                );


        panel.setAlignment(
                Pos.TOP_CENTER
        );


        panel.setPrefWidth(
                240
        );


        panel.setPadding(
                new Insets(
                        15
                )
        );


        panel.setStyle(
                "-fx-background-color: #4b2222;"
        );


        // =================================
        // TITULO
        // =================================

        Label titulo =
                new Label(
                        "THE DICE GAME 2"
                );


        titulo.setStyle(

                "-fx-text-fill: white;" +

                        "-fx-font-size: 19px;" +

                        "-fx-font-weight: bold;"
        );


        // =================================
        // ACTIVIDAD
        // =================================

        Label actividad =
                crearOpcion(
                        "ACTIVIDAD"
                );


        // =================================
        // RENDIMIENTO
        // =================================

        Label rendimiento =
                crearOpcion(
                        "RENDIMIENTO"
                );


        // =================================
        // PERSONAS EN EL SISTEMA
        // =================================

        lblSistema =
                crearOpcion(
                        "Personas en el sistema: 36"
                );


        // =================================
        // TIEMPO
        // =================================

        Label tiempo =
                crearOpcion(
                        "TIEMPO EN EL SISTEMA"
                );


        // =================================
        // TURNOS
        // =================================

        Label turnos =
                new Label(
                        "TURNOS"
                );


        turnos.setStyle(

                "-fx-text-fill: white;" +

                        "-fx-font-size: 19px;" +

                        "-fx-font-weight: bold;"
        );


        // =================================
        // RONDA
        // =================================

        lblRonda =
                new Label(
                        "Ronda: 0"
                );


        lblRonda.setStyle(

                "-fx-text-fill: white;" +

                        "-fx-font-size: 18px;"
        );


        // =================================
        // PERSONAS TERMINADAS
        // =================================

        lblTerminados =
                new Label(
                        "Personas terminadas: 0"
                );


        lblTerminados.setStyle(

                "-fx-text-fill: white;" +

                        "-fx-font-size: 13px;"
        );


        // =================================
        // BOTON
        // =================================

        btnAccion =
                new Button(
                        "INICIAR"
                );


        btnAccion.setPrefSize(
                205,
                45
        );


        btnAccion.setStyle(

                "-fx-background-color: #eee9df;" +

                        "-fx-text-fill: #4b2222;" +

                        "-fx-font-size: 18px;" +

                        "-fx-font-weight: bold;"
        );


        btnAccion.setOnAction(
                event ->
                        accionBoton()
        );


        panel.getChildren()
                .addAll(

                        titulo,

                        actividad,

                        rendimiento,

                        lblSistema,

                        tiempo,

                        turnos,

                        lblRonda,

                        lblTerminados,

                        btnAccion
                );


        return panel;
    }


    // =====================================
    // CREAR OPCION DEL PANEL
    // =====================================

    private Label crearOpcion(
            String texto) {


        Label opcion =
                new Label(
                        texto
                );


        opcion.setPrefWidth(
                205
        );


        opcion.setAlignment(
                Pos.CENTER
        );


        opcion.setStyle(

                "-fx-background-color: #eee9df;" +

                        "-fx-padding: 8;" +

                        "-fx-font-size: 13px;"
        );


        return opcion;
    }


    // =====================================
    // BOTON PRINCIPAL
    // =====================================

    private void accionBoton() {


        // =================================
        // INICIAR
        // =================================

        if (!simulacion.isIniciado()) {


            simulacion.iniciar();


            actualizarColas();


            limpiarDados();


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


            mostrarDados();


            actualizarColas();


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


        actualizarColas();


        actualizarDatos();


        // =================================
        // FINALIZADO
        // =================================

        if (simulacion.isFinalizado()) {


            btnAccion.setText(
                    "FINALIZADO"
            );


            btnAccion.setDisable(
                    true
            );


        } else {


            btnAccion.setText(
                    "LANZAR"
            );
        }
    }


    // =====================================
    // MOSTRAR DADOS
    // =====================================

    private void mostrarDados() {


        Dado[] dadosLogica =
                simulacion.getDados();


        for (int i = 0;
             i < 10;
             i++) {


            dados[i]
                    .setText(

                            String.valueOf(

                                    dadosLogica[i]
                                            .getValor()
                            )
                    );
        }
    }


    // =====================================
    // LIMPIAR DADOS
    // =====================================

    private void limpiarDados() {


        for (int i = 0;
             i < 10;
             i++) {


            dados[i]
                    .setText(
                            "-"
                    );


            ponerDadoRojo(
                    dados[i]
            );
        }


        dadoSeleccionado = -1;
    }


    // =====================================
    // ACTUALIZAR COLAS
    // =====================================

    private void actualizarColas() {


        ColaSimple<Cliente>[] colas =
                simulacion.getColas();


        int[] nuevas =
                simulacion.getFichasNuevas();


        for (int i = 0;
             i < 9;
             i++) {


            int cantidad =
                    colas[i]
                            .cantidad();


            // Solo mostramos las fichas.
            // Ya no mostramos "Personas: X".

            mostrarPersonas(
                    i,
                    cantidad,
                    nuevas[i]
            );
        }
    }


    // =====================================
    // MOSTRAR PERSONAS
    // =====================================

    private void mostrarPersonas(
            int numeroCola,
            int cantidad,
            int nuevas) {


        // =================================
        // BORRAR FICHAS ANTERIORES
        // =================================

        filasPersonas[numeroCola]
                .getChildren()
                .clear();


        int viejas =
                cantidad - nuevas;


        if (viejas < 0) {

            viejas = 0;
        }


        // =================================
        // CREAR FICHAS
        // =================================

        for (int i = 0;
             i < cantidad;
             i++) {


            Circle ficha =
                    new Circle(
                            5
                    );


            // =================================
            // PERSONAS QUE YA ESTABAN
            // =================================

            if (i < viejas) {


                ficha.setFill(
                        Color.GRAY
                );


            } else {


                // =================================
                // PERSONAS NUEVAS
                // =================================

                ficha.setFill(
                        Color.DODGERBLUE
                );
            }


            ficha.setStroke(
                    Color.BLACK
            );


            filasPersonas[numeroCola]
                    .getChildren()
                    .add(
                            ficha
                    );
        }
    }


    // =====================================
    // ACTUALIZAR DATOS
    // =====================================

    private void actualizarDatos() {


        // =================================
        // RONDA
        // =================================

        lblRonda.setText(

                "Ronda: "
                        + simulacion
                        .getRonda()
        );


        // =================================
        // PERSONAS TERMINADAS
        // =================================

        lblTerminados.setText(

                "Personas terminadas: "
                        + simulacion
                        .getPersonasTerminadas()
        );


        // =================================
        // TOTAL DE PERSONAS EN EL SISTEMA
        // =================================

        lblSistema.setText(

                "Personas en el sistema: "
                        + simulacion
                        .personasEnSistema()
        );
    }
}