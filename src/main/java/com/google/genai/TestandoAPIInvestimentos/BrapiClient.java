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
    private static final String BASE_URL = "https://brapi.dev/api/quote/list";
    private static final String DETAIL_URL = "https://brapi.dev/api/quote/";

    public static List<Investment> getInvestments(boolean iniciante) {
        List<Investment> list = new ArrayList<>();
        try {
            String order = iniciante ? "asc" : "desc";
            String urlStr = BASE_URL + "?limit=30&sortBy=change_abs&sortOrder=" + order + "&token=" + TOKEN;

            JSONObject response = new JSONObject(readURL(urlStr));
            JSONArray stocks = response.getJSONArray("stocks");

            int count = 0;
            for (int i = 0; i < stocks.length() && count < 15; i++) {
                JSONObject s = stocks.getJSONObject(i);
                String symbol = s.optString("stock");
                String name = s.optString("name");
                float risk = iniciante ? (float) Math.random() : (float) (1 + Math.random() * 10);

                // Busca detalhada da ação (para pegar o preço real)
                JSONObject detail = new JSONObject(readURL(DETAIL_URL + symbol + "?token=" + TOKEN))
                        .getJSONArray("results").getJSONObject(0);

                double open = detail.optDouble("regularMarketOpen", 0);
                double high = detail.optDouble("regularMarketDayHigh", 0);
                double price = detail.optDouble("regularMarketPrice", 0);

                // Link oficial para consulta
                String url = "https://brapi.dev/api/quote/" + symbol + "?token=" + TOKEN;

                if (open > 0 && price > 0) {
                    list.add(new Investment(symbol, name, risk, open, high, price, url));
                    count++;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar investimentos BRAPI: " + e.getMessage());
        }
        return list;
    }

    private static String readURL(String urlStr) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }
}