package com.example;


import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;

public class Transicion {
    public static void aplicarTransicionHover(Button boton) {
    ScaleTransition stHover = new ScaleTransition(javafx.util.Duration.millis(200), boton);
    stHover.setToX(1.05); // Escala al 105%
    stHover.setToY(1.05);

    ScaleTransition stNormal = new ScaleTransition(javafx.util.Duration.millis(200), boton);
    stNormal.setToX(1); // Escala original
    stNormal.setToY(1);

    boton.setOnMouseEntered(e -> stHover.playFromStart());
    boton.setOnMouseExited(e -> stNormal.playFromStart());
    }
}
