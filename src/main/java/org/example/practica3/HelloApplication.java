package org.example.practica3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

        HelloController controlador =
                new HelloController();

        Scene scene =
                controlador.crearEscena();

        stage.setTitle("The Dice Game 2");

        stage.setScene(scene);

        // TAMAÑO INICIAL DEL JUEGO
        stage.setWidth(1200);
        stage.setHeight(685);

        // Sí permite agrandar y reducir después
        stage.setResizable(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}