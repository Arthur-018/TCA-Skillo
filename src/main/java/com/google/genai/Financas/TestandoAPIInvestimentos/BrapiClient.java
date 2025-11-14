package com.google.genai.Financas.TestandoAPIInvestimentos;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class BrapiClient extends ApiClient {

    public BrapiClient() {
        super("https://brapi.dev/api");
    }

    private double getGuaranteedRisk(int index) {
        double[][] riskBands = {
                {0.1, 1.0}, {0.1, 1.0}, {0.1, 1.0},
                {1.01, 3.0}, {1.01, 3.0}, {1.01, 3.0},
                {3.01, 3.5}, {3.01, 3.5}, {3.01, 3.5}
        };

        int bandIndex = index % 9;

        double min = riskBands[bandIndex][0];
        double max = riskBands[bandIndex][1];
        return min + (Math.random() * (max - min));
    }

    public List<Investment> getInvestments() {
        List<Investment> list = new ArrayList<>();

        String jsonStr = get("/quote/list?limit=15");
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

            double risk = getGuaranteedRisk(list.size());

            list.add(new Investment(symbol, name, price, risk, open, high));
        }

        return list;
    }
}