package com.controller;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class controllerDCA {
    private double balanceInicial;
    private double depositoMensual;
    private double interesAnual;
    private int duracion; // Duracion en años

    // Setter for balanceInicial
    public void setBalanceInicial(double balanceInicial) {
        this.balanceInicial = balanceInicial;
    }

    public void setDeposito(double depositoMensual) {
        this.depositoMensual = depositoMensual;
    }

    public void setInteresAnual(double interesAnual) {
        this.interesAnual = interesAnual;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @FXML
    private Label resultadoDCA; // Label para mostrar el resultado

    public void resultadoDCA() {
        double balance = balanceInicial;
        double interesMensual = interesAnual / 12 / 100; // Convertir el interés anual a mensual en decimal
        int meses = (int) (duracion * 12); // Convertir la duración en años a meses

        for (int i = 0; i < meses; i++) {
            balance += depositoMensual; // Agregar el depósito mensual
            balance += balance * interesMensual; // Calcular y agregar el interés mensual
        }

        // Formatear el balance con separadores de miles y dos decimales
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String balanceFormateado = numberFormat.format(balance);

        // Mostrar el resultado en el Label
        resultadoDCA.setText(String.format(balanceFormateado + " €"));
    }
}
