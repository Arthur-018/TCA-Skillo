package com.google.genai.Financas.TestandoAPIInvestimentos;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class BrapiClient extends ApiClient {

    public BrapiClient() {
        super("https://brapi.dev/api");
    }

    public List<Investment> getInvestments() {
        List<Investment> list = new ArrayList<>();

        String jsonStr = get("/quote/list?limit=5");
        JSONObject obj = new JSONObject(jsonStr);

        JSONArray stocks = obj.optJSONArray("stocks");
        if (stocks == null) return list;

        for (int i = 0; i < stocks.length(); i++) {
            JSONObject s = stocks.getJSONObject(i);

            String symbol = s.optString("stock");
            String name = s.optString("name");
            double price = s.optDouble("close", 0);
            double open = s.optDouble("open", price);
            double high = s.optDouble("high", price);
            double risk = Math.random() * 3;

            list.add(new Investment(symbol, name, price, risk, open, high));
        }
        List<Investment> teste = getInvestments();


        return list;
    }
}