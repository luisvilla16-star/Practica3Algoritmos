module org.example.practica3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.practica3 to javafx.fxml;
    exports org.example.practica3;
}