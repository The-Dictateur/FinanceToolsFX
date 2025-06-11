package com.controller;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class controllerHipoteca {

    private double precioVivienda;
    private double ahorroAportado;
    private int years;
    private Double spinnerInteres; // Duracion en años
    

    // Setters
    public void setPrecioVivienda(double precioVivienda) {
        this.precioVivienda = precioVivienda;
    }

    public void setDinero(double ahorroAportado) {
        this.ahorroAportado = ahorroAportado;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void setInteresAnual(double spinnerInteres) {
        this.spinnerInteres = spinnerInteres;
    }

    @FXML
    private Label resultadoHipoteca; // Label para mostrar el resultado

    @FXML
    private Label resultadoTotal;

    @FXML
    private Label resultadoInteres;

    public void resultadoHipoteca() {
        double cantidadPrestamo = precioVivienda - ahorroAportado;
        double interesMensual = spinnerInteres / 100 / 12;
        int meses = years * 12;

        double cuotaMensual = (cantidadPrestamo * interesMensual) / (1 - Math.pow(1 + interesMensual, - meses));
        double totalPagado = cuotaMensual * meses;
        double totalIntereses = totalPagado - cantidadPrestamo;

        // Formatear el balance con separadores de miles y dos decimales
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String cuotaFormateado = numberFormat.format(cuotaMensual);
        String totalFormateado = numberFormat.format(totalPagado);
        String interesFormateado = numberFormat.format(totalIntereses);

        // Mostrar resultados
        resultadoHipoteca.setText(String.format(cuotaFormateado + " €"));
        resultadoTotal.setText(String.format(totalFormateado + " €"));
        resultadoInteres.setText(String.format(interesFormateado + " €"));
    }
}
