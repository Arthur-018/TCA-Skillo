package com.google.genai.TestandoAPIInvestimentos;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EODHDClient {
    private static final String TOKEN = "demo"; // substitua pelo seu token válido da EODHD
    private static final String SCREENER_URL = "https://eodhd.com/api/screener";
    private static final String REALTIME_URL = "https://eodhd.com/api/real-time/";

    public static List<Investment> getInvestments(boolean iniciante) {
        List<Investment> list = new ArrayList<>();
        try {
            String riskFilter = iniciante ? "low" : "high";
            String screenerURL = SCREENER_URL + "?sort=market_capitalization.desc&limit=30&api_token=" + TOKEN + "&fmt=json";

            JSONArray results = new JSONArray(readURL(screenerURL));

            int count = 0;
            for (int i = 0; i < results.length() && count < 15; i++) {
                JSONObject s = results.getJSONObject(i);
                String code = s.optString("Code");
                String name = s.optString("Name");
                float risk = iniciante ? (float) Math.random() : (float) (1 + Math.random() * 10);

                // Dados reais de preço
                String realTimeURL = REALTIME_URL + code + ".US?api_token=" + TOKEN + "&fmt=json";
                JSONObject detail = new JSONObject(readURL(realTimeURL));

                double open = detail.optDouble("open", 0);
                double high = detail.optDouble("high", 0);
                double price = detail.optDouble("close", 0);

                String url = "https://finance.yahoo.com/quote/" + code;

                if (open > 0 && price > 0) {
                    list.add(new Investment(code, name, risk, open, high, price, url));
                    count++;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar investimentos EODHD: " + e.getMessage());
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