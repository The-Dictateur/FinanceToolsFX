package com.example;

import java.util.Scanner;

public class ValorInicial {
    public static void inicial() {
        // Tu lógica aquí
        Scanner scanner = new Scanner(System.in);
        
        // Entrada de datos
        System.out.print("Ingrese el capital inicial (€): ");
        double capitalInicial = scanner.nextDouble();
        
        System.out.print("Ingrese el porcentaje de cambio actual (- para pérdidas, + para ganancias): ");
        double porcentajeCambio = scanner.nextDouble();
        
        System.out.print("Ingrese la cantidad adicional a invertir (€): ");
        double nuevaInversion = scanner.nextDouble();
        
        // Cálculo de la pérdida/ganancia en euros
        double cambioAbsoluto = capitalInicial * (porcentajeCambio / 100);
        
        // Nuevo capital total después de la inversión
        double nuevoCapital = capitalInicial + nuevaInversion;
        
        // Nuevo porcentaje de cambio
        double nuevoPorcentajeCambio = (cambioAbsoluto / nuevoCapital) * 100;
        
        // Resultados
        System.out.printf("\nDespués de invertir %.2f€, el nuevo porcentaje de cambio será: %.2f%%\n", nuevaInversion, nuevoPorcentajeCambio);
        
        scanner.close();
    }
}