package com.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.scene.chart.PieChart;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.Transicion;
import com.opencsv.CSVWriter;

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

    @FXML
    private Label capitalInicialResultado;

    @FXML
    private Label ahorroSinInteresResultado;

    @FXML
    private Label interesResultado;

    @FXML
    private PieChart pieChartDCA; // PieChart para mostrar el gráfico

    @FXML
    private Button btnDescargarXlsx;

    public void initialize() {
        Transicion.aplicarTransicionHover(btnDescargarXlsx);
        btnDescargarXlsx.setOnAction(event -> {
            try {
                descargarXlsx();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    public void resultadoDCA() {
        double ahorroSinInteres = 0;
        double interes = 0;
        double balance = balanceInicial;
        double interesMensual = interesAnual / 12 / 100; // Convertir el interés anual a mensual en decimal
        int meses = (int) (duracion * 12); // Convertir la duración en años a meses

        for (int i = 0; i < meses; i++) {
            balance += depositoMensual; // Agregar el depósito mensual
            balance += balance * interesMensual; // Calcular y agregar el interés mensual
            ahorroSinInteres += depositoMensual; // Agregar el ahorro sin interés
            interes += balance * interesMensual; // Calcular el interés acumulado

        }

        // Formatear el balance con separadores de miles y dos decimales
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String balanceFormateado = numberFormat.format(balance);
        String interesFormateado = numberFormat.format(interes);
        String ahorroSinInteresFormateado = numberFormat.format(ahorroSinInteres);
        String balanceInicialFormateado = numberFormat.format(balanceInicial);

        // Mostrar el resultado en el Label
        resultadoDCA.setText(String.format(balanceFormateado + " €"));
        capitalInicialResultado.setText(balanceInicialFormateado + " €");
        ahorroSinInteresResultado.setText(ahorroSinInteresFormateado + " €");
        interesResultado.setText(interesFormateado + " €");

        pieChartDCA.getData().clear(); // Limpiar datos anteriores del gráfico
        PieChart.Data sliceCapitalInicial = new PieChart.Data("Capaital Inicial", balanceInicial);
        PieChart.Data sliceAhorroSinInteres = new PieChart.Data("Ahorro sin interes", ahorroSinInteres);
        PieChart.Data sliceIntereses = new PieChart.Data("Intereses", interes);

        pieChartDCA.getData().addAll(sliceCapitalInicial, sliceAhorroSinInteres, sliceIntereses); // Agregar los nuevos datos al gráfico

        pieChartDCA.setTitle("Gráfico"); // Establecer el título del gráfico
        pieChartDCA.setLabelsVisible(true); // Hacer visibles las etiquetasº
        pieChartDCA.setLegendVisible(true); // Hacer visible la leyenda
        pieChartDCA.setLabelLineLength(10); // Establecer la longitud de la línea de etiqueta
    }

    private void descargarXlsx() throws IOException {
        
        String userHome = System.getProperty("user.home");
        String downloadDir = Paths.get(userHome, "Downloads").toString(); // Carpeta Descargas
        Files.createDirectories(Paths.get(downloadDir)); // Crear la carpeta si no existe

        // Ruta completa del archivo
        String nombreArchivo = Paths.get(downloadDir, "Resultado_DCA.csv").toString();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            CSVWriter csvWriter = new CSVWriter(writer, ';', 
                CSVWriter.NO_QUOTE_CHARACTER, 
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                CSVWriter.DEFAULT_LINE_END);
            String[] header = { "Capital Inicial", "Ahorro sin Interes", "Intereses", "Total" };
            csvWriter.writeNext(header);

            String ahorroSinInteresLimpio = ahorroSinInteresResultado.getText().replace(" €", "").replace(".", "").replace(",", ".");
            String interesLimpio = interesResultado.getText().replace(" €", "").replace(".", "").replace(",", ".");
            String totalLimpio = resultadoDCA.getText().replace(" €", "").replace(".", "").replace(",", ".");

            String[] data = { String.valueOf(balanceInicial), String.valueOf(ahorroSinInteresLimpio),
                    String.valueOf(interesLimpio), String.valueOf(totalLimpio) };
            csvWriter.writeNext(data);

            csvWriter.close();
            System.out.println("Archivo CSV creado: " + nombreArchivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
