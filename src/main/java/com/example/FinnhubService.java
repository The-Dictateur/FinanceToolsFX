package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FinnhubService {
    private final String apiKey;

    public FinnhubService(String apiKey) {
        this.apiKey = apiKey;
    }

    // Funcion para evolver en formato String la informacion del Stock (JSON)
    public String getStockInfo(String symbol) throws Exception {
        // Definimos URL Finnhub para la funcion que necesitamos
        String endpoint = Constants.BASE_URL + "/quote?symbol=" + symbol + "&token=" + apiKey;
        URL url = new URL(endpoint);
        // Abrimos la conexion Http
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader inputStream = new BufferedReader (new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = inputStream.readLine()) != null) {
            response.append(line);
        }
        inputStream.close();

        return response.toString();
    }
}
