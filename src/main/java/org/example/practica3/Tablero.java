package org.example.practica3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tablero {

    private Simulacion simulacion;

    private Label[] dados;

    private FlowPane[] espaciosDados;

    private FlowPane[] filasPersonas;

    private int dadoSeleccionado;

    public Tablero(
            Simulacion simulacion) {

        this.simulacion =
                simulacion;

        dados =
                new Label[10];

        espaciosDados =
                new FlowPane[10];

        filasPersonas =
                new FlowPane[9];

        dadoSeleccionado = -1;
    }

    // =====================================
    // CREAR TABLERO
    // =====================================

    public GridPane crearTablero() {

        GridPane tablero =
                new GridPane();

        tablero.setHgap(15);
        tablero.setVgap(15);

        tablero.setPadding(
                new Insets(20)
        );

        tablero.setAlignment(
                Pos.CENTER
        );

        for (int i = 0; i < 10; i++) {

            VBox estacion =
                    crearEstacion(i);

            int columna = i % 5;
            int fila = i / 5;

            tablero.add(
                    estacion,
                    columna,
                    fila
            );
        }

        return tablero;
    }

    // =====================================
    // CREAR ESTACIÓN
    // =====================================

    private VBox crearEstacion(
            int numero) {

        Label titulo =
                new Label(
                        "Estación "
                                + (numero + 1)
                );

        titulo.setStyle(
                "-fx-font-weight: bold;"
        );

        ImageView imagen =
                crearImagenPersona(
                        numero + 1
                );

        espaciosDados[numero] =
                new FlowPane();

        espaciosDados[numero]
                .setHgap(4);

        espaciosDados[numero]
                .setAlignment(Pos.CENTER);

        Label dado =
                crearDado(numero);

        dados[numero] = dado;

        espaciosDados[numero]
                .getChildren()
                .add(dado);

        VBox estacion =
                new VBox(
                        5,
                        titulo,
                        imagen,
                        espaciosDados[numero]
                );

        estacion.setAlignment(
                Pos.CENTER
        );

        estacion.setPadding(
                new Insets(8)
        );

        estacion.setPrefSize(
                190,
                140
        );

        estacion.setStyle(
                "-fx-border-color: gray;"
                        + "-fx-border-radius: 5;"
        );

        // Click en la estación
        // para mover un dado

        estacion.setOnMouseClicked(
                e -> seleccionarEstacion(numero)
        );

        // Cola después de la estación
        // excepto estación 10

        if (numero < 9) {

            filasPersonas[numero] =
                    new FlowPane();

            filasPersonas[numero]
                    .setHgap(2);

            filasPersonas[numero]
                    .setVgap(2);

            filasPersonas[numero]
                    .setPrefWrapLength(150);

            estacion.getChildren()
                    .add(
                            filasPersonas[numero]
                    );
        }

        return estacion;
    }

    // =====================================
    // IMAGEN
    // =====================================

    private ImageView crearImagenPersona(
            int numero) {

        ImageView imagen =
                new ImageView();

        try {

            Image foto =
                    new Image(
                            getClass()
                                    .getResourceAsStream(
                                            "/org/example/practica3/IMAGENES/persona"
                                                    + numero
                                                    + ".jpg"
                                    )
                    );

            imagen.setImage(foto);

        } catch (Exception e) {

            System.out.println(
                    "No se encontró persona"
                            + numero
                            + ".jpg"
            );
        }

        imagen.setFitWidth(55);
        imagen.setFitHeight(55);

        imagen.setPreserveRatio(true);

        return imagen;
    }

    // =====================================
    // CREAR DADO
    // =====================================

    private Label crearDado(
            int numero) {

        Label dado =
                new Label("-");

        dado.setPrefSize(
                32,
                32
        );

        dado.setAlignment(
                Pos.CENTER
        );

        ponerDadoRojo(dado);

        dado.setOnMouseClicked(e -> {

            e.consume();

            seleccionarDado(numero);
        });

        return dado;
    }

    // =====================================
    // SELECCIONAR DADO
    // =====================================

    private void seleccionarDado(
            int numero) {

        dadoSeleccionado = numero;

        for (int i = 0; i < 10; i++) {

            ponerDadoRojo(
                    dados[i]
            );
        }

        dados[numero].setStyle(
                "-fx-background-color: orange;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-weight: bold;"
                        + "-fx-border-color: black;"
        );
    }

    // =====================================
    // SELECCIONAR ESTACIÓN
    // =====================================

    private void seleccionarEstacion(
            int estacion) {

        if (dadoSeleccionado == -1) {
            return;
        }

        simulacion.moverDado(
                dadoSeleccionado,
                estacion
        );

        moverDadoVisual(
                dadoSeleccionado,
                estacion
        );

        dadoSeleccionado = -1;
    }

    // =====================================
    // MOVER DADO VISUAL
    // =====================================

    private void moverDadoVisual(
            int numeroDado,
            int estacion) {

        Label dado =
                dados[numeroDado];

        if (dado.getParent()
                instanceof FlowPane) {

            FlowPane anterior =
                    (FlowPane)
                            dado.getParent();

            anterior
                    .getChildren()
                    .remove(dado);
        }

        espaciosDados[estacion]
                .getChildren()
                .add(dado);

        ponerDadoRojo(dado);
    }

    // =====================================
    // COLOR DEL DADO
    // =====================================

    private void ponerDadoRojo(
            Label dado) {

        dado.setStyle(
                "-fx-background-color: #cc3333;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-weight: bold;"
                        + "-fx-border-color: black;"
        );
    }

    // =====================================
    // MOSTRAR VALORES
    // =====================================

    public void mostrarDados() {

        Dado[] dadosLogica =
                simulacion.getDados();

        for (int i = 0; i < 10; i++) {

            dados[i].setText(
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

    public void limpiarDados() {

        for (int i = 0; i < 10; i++) {

            dados[i].setText("-");
        }
    }

    // =====================================
    // ACTUALIZAR COLAS
    // =====================================

    public void actualizarColas() {

        ColaSimple<Cliente>[] colas =
                simulacion.getColas();

        int[] nuevas =
                simulacion.getFichasNuevas();

        for (int i = 0; i < 9; i++) {

            mostrarPersonas(
                    i,
                    colas[i].cantidad(),
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

        FlowPane fila =
                filasPersonas[numeroCola];

        fila.getChildren().clear();

        int antiguas =
                cantidad - nuevas;

        if (antiguas < 0) {
            antiguas = 0;
        }

        // Grises

        for (int i = 0;
             i < antiguas;
             i++) {

            Circle persona =
                    new Circle(5);

            persona.setFill(
                    Color.GRAY
            );

            fila.getChildren()
                    .add(persona);
        }

        // Azules

        for (int i = 0;
             i < nuevas;
             i++) {

            Circle persona =
                    new Circle(5);

            persona.setFill(
                    Color.DODGERBLUE
            );

            fila.getChildren()
                    .add(persona);
        }
    }
}