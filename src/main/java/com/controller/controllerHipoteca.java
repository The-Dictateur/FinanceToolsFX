package com.controller;

public class controllerHipoteca {

    private double precioVivienda;
    private double ahorroAportado;
    private int years;
    private Double spinnerInteres; // Duracion en años
    

    // Setter for balanceInicial
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

    public void resultadoHipoteca() {
        double cantidadPrestamo = precioVivienda - ahorroAportado;
        double interesMensual = spinnerInteres / 100 / 12;
        int meses = years * 12;

        double cuotaMensual = (cantidadPrestamo * interesMensual) / (1 - Math.pow(1 + interesMensual, -meses));
        double totalPagado = cuotaMensual * meses;
        double totalIntereses = totalPagado - cantidadPrestamo;

        // Mostrar resultados
        System.out.println("Cuota mensual: " + String.format("%.2f", cuotaMensual));
        System.out.println("Total pagado: " + String.format("%.2f", totalPagado));
        System.out.println("Total intereses: " + String.format("%.2f", totalIntereses));
    }
}
