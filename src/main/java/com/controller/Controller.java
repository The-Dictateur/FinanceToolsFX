package com.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.Constants;
import com.example.FinnhubService;
import com.example.Transicion;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private TextField visiblePorcentage;

    @FXML
    private TextField resultadoVisible;

    @FXML
    private Button btnInicial;

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField valorSymbol;

    @FXML
    private TextField symbol;

    @FXML
    private TextField cambioSymbol;

    @FXML
    private Button btndca;

    @FXML
    private Button btnHipoteca;

    @FXML
    private Spinner<Double> balanceInicial;

    @FXML
    private Spinner<Double> deposito;

    @FXML
    private Spinner<Double> interesAnual;
    
    @FXML
    private Spinner<Integer> duracion;

    @FXML
    private VBox vboxDCA;

    @FXML
    private Label resultadoDCA;

    @FXML
    private ListView<String> sugerenciasStock;

    // Hipoteca
    @FXML
    private Slider dineroSlider;
    @FXML
    private Slider yearSlider;
    @FXML
    private TextField precioVivienda;
    @FXML
    private TextField ahorroAportado;
    @FXML
    private TextField years;
    @FXML
    private Spinner<Double> spinnerInteres;

    private final HttpClient client = HttpClient.newHttpClient();

    @FXML
    public void initialize() {
        // Configurar el botón "Calcular" para "Valor Inicial"
        btnInicial.setOnAction(event -> calcularValorInicial());

        btndca.setOnAction(event -> {
            try {
                calcularDCA();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnBuscar.setOnAction(event -> buscarStock());
        btnHipoteca.setOnAction(event -> {
            try {
                calcularHipoteca();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        symbol.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() >= 1) {
                buscarSugerencias(newText);
            } else {
                sugerenciasStock.getItems().clear(); // Limpiar sugerencias si el campo está vacío
            }
        });
        
        sugerenciasStock.setOnMouseClicked(event -> {
            String selectedSymbol = sugerenciasStock.getSelectionModel().getSelectedItem();
            if (selectedSymbol != null) {
                symbol.setText(selectedSymbol);
                sugerenciasStock.getItems().clear(); // Limpiar sugerencias después de seleccionar
                sugerenciasStock.setVisible(false); // Ocultar la lista de sugerencias
            }
        });

        dineroSlider.valueProperty().addListener((obs, oldText, newText) -> {
            try {
                double precio = Double.parseDouble(precioVivienda.getText());
                dineroSlider.setMin(0);
                dineroSlider.setMax(precio);
                dineroSlider.setSnapToTicks(true);
                dineroSlider.setMajorTickUnit(100);
                dineroSlider.setMinorTickCount(0);
                dineroSlider.setBlockIncrement(100);
                ahorroAportado.setText(String.format("%.2f", dineroSlider.getValue()));
            } catch (NumberFormatException e) {
                dineroSlider.setValue(0);
            }
        });

        yearSlider.valueProperty().addListener((obs, oldText, newText) -> {
            try {
                yearSlider.setMin(0);
                yearSlider.setMax(30);
                yearSlider.setSnapToTicks(true);
                yearSlider.setMajorTickUnit(1);
                yearSlider.setMinorTickCount(0);
                yearSlider.setBlockIncrement(1);
                years.setText(String.format("%.0f", yearSlider.getValue()));
            } catch (NumberFormatException e) {
                yearSlider.setValue(0);
            }
        });

        // Aplicar animación hover a los botones
        Transicion.aplicarTransicionHover(btnInicial);
        Transicion.aplicarTransicionHover(btnBuscar);
        Transicion.aplicarTransicionHover(btndca);
        Transicion.aplicarTransicionHover(btnHipoteca);

        //Inicializar los valores de los spinners
        balanceInicial.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000000.0, 100.0, 1.0));
        deposito.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000000.0, 50.0, 1.0));
        interesAnual.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0, 5.0, 0.1));
        duracion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10, 1));
        
        // Inicializar i dar formato a spinner de Interes anual
        spinnerInteres.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0, 2.5, 0.01));
    
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = 
        (SpinnerValueFactory.DoubleSpinnerValueFactory) spinnerInteres.getValueFactory();

        valueFactory.setConverter(new javafx.util.StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                if (value == null) return "";
                return String.format("%.2f %%", value);
            }

            @Override
            public Double fromString(String string) {
                // TODO Auto-generated method stub
                return Double.valueOf(string.replace("%", "").replace(" ", "").replace(",", "."));
            }
        });
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

    private void calcularDCA() throws IOException {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dca.fxml"));
        Parent dca = loader.load();

        controllerDCA dcaController = loader.getController();

        // Obtener los valores de los spinners
        dcaController.setBalanceInicial((double) balanceInicial.getValue());
        dcaController.setDeposito((double) deposito.getValue());
        dcaController.setInteresAnual((double) interesAnual.getValue());
        dcaController.setDuracion((int) duracion.getValue());

        dcaController.resultadoDCA(); // Llamar al método para calcular el resultado
        

        Stage nuevaVentana = new Stage();
        nuevaVentana.setTitle("Resultado DCA");
        nuevaVentana.setScene(new Scene(dca));
        nuevaVentana.show();

        } catch (NumberFormatException e) {
            resultadoVisible.setText("Error");
        }
    }

    private void buscarStock() {
        try {

        FinnhubService service = new FinnhubService(Constants.API_KEY);

        // Limpiar los campos de texto antes de buscar
        valorSymbol.clear();
        cambioSymbol.clear();

        // Obtener el símbolo del stock desde el campo de texto
        String symbol = this.symbol.getText().toUpperCase();

        // Llamar al método StockFetcher para obtener los datos del stock
        String resultado = service.getStockInfo(symbol);

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
        sugerenciasStock.setVisible(false); // Ocultar la lista de sugerencias después de buscar
        } catch (NumberFormatException e) {
            e.printStackTrace();
            valorSymbol.setText("Error al buscar el stock.");
        } catch (Exception e) {
            e.printStackTrace();
            valorSymbol.setText("Error al buscar el stock.");
        }
    }
    private void buscarSugerencias(String symbol) {
        String endpoint = "https://finnhub.io/api/v1/search?q=" + symbol + "&token=" + Constants.API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    JSONObject json = new JSONObject(response);
                    JSONArray resultArray = json.getJSONArray("result");
                    Platform.runLater(() -> {
                        sugerenciasStock.getItems().clear(); // Limpiar sugerencias anteriores
                        sugerenciasStock.setVisible(true);
                        for (int i = 0; i < resultArray.length(); i++) {
                            JSONObject stock = resultArray.getJSONObject(i);
                            String symbolSugerencia = stock.getString("symbol");
                            if (symbolSugerencia.contains(".")) {
                                continue; // Ignorar símbolos que contienen "."
                            }
                            sugerenciasStock.getItems().add(symbolSugerencia);
                        }
                    });
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void calcularHipoteca() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hipoteca.fxml"));
            Parent hipoteca = loader.load();

            controllerHipoteca hipotecaController = loader.getController();

            // Obtener los valores de los spinners
            hipotecaController.setPrecioVivienda(Double.parseDouble(precioVivienda.getText()));
            hipotecaController.setDinero(Double.parseDouble(ahorroAportado.getText().replace(",", ".")));
            hipotecaController.setYears(Integer.parseInt(years.getText()));
            hipotecaController.setInteresAnual(spinnerInteres.getValue());

            hipotecaController.resultadoHipoteca(); // Llamar al método para calcular el resultado

            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Hipoteca");
            nuevaVentana.setScene(new Scene(hipoteca));
            nuevaVentana.show();

        } catch (NumberFormatException e) {
            e.printStackTrace();
            precioVivienda.setText("Error al calcular la hipoteca.");
        }
    }
}
