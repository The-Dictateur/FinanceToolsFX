package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StockFetcher {

    public static String stock(String symbol) throws Exception {
        String apiKey = "d0039p1r01qud9qlgga0d0039p1r01qud9qlggag";
        String endpoint = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + apiKey;

        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        // Devolver la respuesta como String
        return response.toString();
    }
}