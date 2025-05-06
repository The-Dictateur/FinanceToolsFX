package com.example;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField valorInicial;

    @FXML
    private TextField inicialPorcentage;

    @FXML
    private TextField inversionInicial;

    @FXML
    private TextField resultadoInicial;

    @FXML
    private TextField valorVisible;

    @FXML
    private TextField visiblePorcentage;

    @FXML
    private TextField inversionVisible;

    @FXML
    private TextField resultadoVisible;

    @FXML
    private Button btnInicial;

    @FXML
    private Button btnVisible;

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField valorSymbol;

    @FXML
    private TextField symbol;

    @FXML
    private TextField cambioSymbol;

    @FXML
    public void initialize() {
        // Configurar el botón "Calcular" para "Valor Inicial"
        btnInicial.setOnAction(event -> calcularValorInicial());
        // Configurar el botón "Calcular" para "Valor Visible"
        btnVisible.setOnAction(event -> calcularValorVisible());
        btnBuscar.setOnAction(event -> buscarStock());

        // Aplicar animación hover a los botones
        Transicion.aplicarTransicionHover(btnInicial);
        Transicion.aplicarTransicionHover(btnVisible);
        Transicion.aplicarTransicionHover(btnBuscar);
    }

    private void calcularValorInicial() {
        try {
            // Obtener valores de los campos de texto
            double valor = Double.parseDouble(valorInicial.getText());
            double porcentaje = Double.parseDouble(inicialPorcentage.getText());
            double inversion = Double.parseDouble(inversionInicial.getText());

            // Realizar el cálculo
            double cambioAbsoluto = valor * (porcentaje / 100);
            double nuevoCapital = valor + inversion;
            double nuevoPorcentaje = (cambioAbsoluto / nuevoCapital) * 100;

            // Mostrar el resultado
            resultadoInicial.setText(String.format("%.2f%%", nuevoPorcentaje));
        } catch (NumberFormatException e) {
            resultadoInicial.setText("Error");
        }
    }

    private void calcularValorVisible() {
        try {
            // Obtener valores de los campos de texto
            double valorVisible = Double.parseDouble(this.valorVisible.getText().replace(",", "."));
            double porcentaje = Double.parseDouble(visiblePorcentage.getText().replace(",", "."));
            double inversion = Double.parseDouble(inversionVisible.getText().replace(",", "."));
    
            // Calcular el valor inicial a partir del valor visible y el porcentaje
            double valorInicial;
            if (porcentaje >= 0) {
                // Si el porcentaje es positivo
                valorInicial = valorVisible * (1 + (porcentaje / 100));
            } else {
                // Si el porcentaje es negativo
                valorInicial = valorVisible / (1 + (porcentaje / 100));
            }
    
            // Calcular el nuevo capital sumando la inversión al valor inicial
            double cambioAbsoluto = valorInicial * (porcentaje / 100);
            double nuevoCapital = valorInicial + inversion;
    
            // Calcular el nuevo porcentaje de cambio (DCA)
            double nuevoPorcentaje = (cambioAbsoluto / nuevoCapital) * 100;
    
            // Mostrar el resultado en el campo de texto
            resultadoVisible.setText(String.format("%.2f%%", nuevoPorcentaje));
        } catch (NumberFormatException e) {
            resultadoVisible.setText("Error");
        }
    }

    private void buscarStock() {
        try {

        // Obtener el símbolo del stock desde el campo de texto
        String symbol = this.symbol.getText().toUpperCase();

        // Llamar al método StockFetcher para obtener los datos del stock
        String resultado = StockFetcher.stock(symbol);

        // Procesar el JSON
        JSONObject json = new JSONObject(resultado);

        // Extraer los valores relevantes como String
        String precioActual = String.valueOf(json.getDouble("c"));
        String cambio = String.valueOf(json.getDouble("d"));
        String porcentajeCambio = String.valueOf(json.getDouble("dp"));
        // String maximo = String.valueOf(json.getDouble("h"));
        // String minimo = String.valueOf(json.getDouble("l"));
        // String apertura = String.valueOf(json.getDouble("o"));
        // String cierreAnterior = String.valueOf(json.getDouble("pc"));

        valorSymbol.setText(precioActual);
        cambioSymbol.setText(cambio + " | " + porcentajeCambio + "%");

        // Aplicar estilos dinámicos según el valor de "cambio"
        cambioSymbol.getStyleClass().removeAll("text-positive", "text-negative"); // Eliminar clases previas
        if (Double.parseDouble(cambio) >= 0) {
            cambioSymbol.getStyleClass().add("text-positive"); // Agregar clase positiva
            valorSymbol.getStyleClass().add("text-positive"); // Agregar clase positiva
        } else {
            cambioSymbol.getStyleClass().add("text-negative"); // Agregar clase negativa
            valorSymbol.getStyleClass().add("text-negative"); // Agregar clase negativa
        }
        } catch (Exception e) {
            e.printStackTrace();
            valorSymbol.setText("Error al buscar el stock.");
        }
    }
}
