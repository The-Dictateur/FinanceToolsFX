package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FinnhubService {
    private final String apiKey;
    private final HttpClient httpClient;

    public FinnhubService(String apiKey, HttpClient httpClient) {
        this.apiKey = apiKey;
        this.httpClient = httpClient;
    }

    // Funcion para evolver en formato String la informacion del Stock (JSON)
    public String getStockInfo(String symbol) throws Exception {
        String endpoint = Constants.BASE_URL + "/quote?symbol=" + symbol + "&token=" + apiKey;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
