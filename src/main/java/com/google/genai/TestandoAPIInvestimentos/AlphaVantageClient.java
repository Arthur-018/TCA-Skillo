package com.google.genai.TestandoAPIInvestimentos;

import java.net.URI;
import java.net.http.*;
import org.json.JSONObject;
import java.util.*;

public class AlphaVantageClient {
    private static final String API_KEY = "6XS6KZWX0R6SVQ5G";
    private static final String BASE_URL =
            "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s";

    public static List<Float> getDailyPrices(String symbol) throws Exception {
        String url = String.format(BASE_URL, symbol, API_KEY);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject json = new JSONObject(response.body());
        if (!json.has("Time Series (Daily)")) {
            System.out.println(" Nenhum dado encontrado para " + symbol);
            return Collections.emptyList();
        }

        JSONObject series = json.getJSONObject("Time Series (Daily)");
        List<Float> closes = new ArrayList<>();

        for (String date : series.keySet()) {
            closes.add((float) series.getJSONObject(date).getDouble("4. close"));
        }

        Collections.reverse(closes);
        return closes;
    }
}
