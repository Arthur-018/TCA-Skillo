package com.google.genai.Financas.TestandoAPIInvestimentos;

import com.google.genai.Financas.TestandoAPIInvestimentos.Investment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BrapiClient extends com.google.genai.Financas.API.ApiClient {

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
            double open = s.optDouble("open", 0);
            double high = s.optDouble("high", 0);

            double rendimentoMensal = price > 0 && open > 0 ? ((price - open) / open) * 100 : 0;
            double risk = Math.random() * 3; // risco fictício

            list.add(new Investment(symbol, name, price, risk, open, high, rendimentoMensal));
        }

        return list;
    }
}