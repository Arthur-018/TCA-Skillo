package com.google.genai.TestandoAPIInvestimentos;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BrapiClient {
    private static final String TOKEN = "4KN8PE9RqgVAxzxjRuRTTP";

    private static final String QUOTE_URL = "https://brapi.dev/api/quote/";

    private static final String[] STOCKS = {"VALE3", "PETR4", "ITUB4", "BBDC4", "MGLU3"};

    private static final long API_PAUSE_MS = 3000;

    public static List<Investment> getInvestments() {
        List<Investment> list = new ArrayList<>();

        try {
            for (String symbol : STOCKS) {

                String urlStr = QUOTE_URL + symbol + "?token=" + TOKEN;

                JSONObject response = new JSONObject(readURL(urlStr, API_PAUSE_MS));

                JSONArray stocksArray = response.optJSONArray("results");

                if (stocksArray == null || stocksArray.length() == 0) {
                    System.err.println("❌ Brapi: Não foi possível obter resultados para " + symbol + ". Pulando...");
                    continue;
                }

                JSONObject stockData = stocksArray.getJSONObject(0);

                String name = stockData.optString("longName", symbol);

                double open = stockData.optDouble("regularMarketOpen", 0);
                double high = stockData.optDouble("regularMarketDayHigh", 0);
                double price = stockData.optDouble("regularMarketPrice", 0);

                double price30DaysAgo = stockData.optDouble("regularMarketPreviousClose", 0);

                float risk = (float) (Math.random() * 5.0);
                String url = "https://brapi.dev/api/quote/" + symbol;

                if (price > 0) {
                    list.add(new Investment(symbol, name, risk, open, high, price, price30DaysAgo, url));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar investimentos Brapi (B3): " + e.getMessage());
        }
        return list;
    }

    private static String readURL(String urlStr, long sleepTime) throws Exception {
        Thread.sleep(sleepTime);

        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorSb = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) errorSb.append(errorLine);
                System.err.println("Resposta de Erro da API: " + errorSb.toString());
            }
            throw new Exception("Falha na requisição. Código de resposta: " + responseCode);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }
}