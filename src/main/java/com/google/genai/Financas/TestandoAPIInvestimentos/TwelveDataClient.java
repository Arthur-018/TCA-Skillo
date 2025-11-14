package com.google.genai.Financas.TestandoAPIInvestimentos;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class TwelveDataClient extends ApiClient {

    private static final String API_KEY = "6f58c2f5d2194c5ba87cd8c18b6a8599";

    public TwelveDataClient() {
        super("https://api.twelvedata.com");
    }

    public List<Investment> getStocks() {
        List<Investment> list = new ArrayList<>();
        String[] symbols = {"AAPL", "MSFT", "GOOGL"};

        for (String sym : symbols) {
            String json = get("/quote?symbol=" + sym + "&apikey=" + API_KEY);
            JSONObject obj = new JSONObject(json);

            if (obj.has("code")) {
                System.out.println("Erro ao buscar " + sym + ": " + obj.optString("message"));
                continue;
            }

            double price = obj.optDouble("close", 0);
            double open = obj.optDouble("open", price);
            double high = obj.optDouble("high", price);
            double risk = Math.random() * 3;

            list.add(new Investment(sym, obj.optString("name", sym), price, risk, open, high));
        }

        return list;
    }

    public List<Investment> getCryptos() {
        List<Investment> list = new ArrayList<>();
        String[] cryptos = {"BTC/USD", "ETH/USD", "SOL/USD"};

        for (String c : cryptos) {
            String json = get("/quote?symbol=" + c + "&apikey=" + API_KEY);
            JSONObject obj = new JSONObject(json);

            if (obj.has("code")) {
                System.out.println("Erro ao buscar " + c + ": " + obj.optString("message"));
                continue;
            }

            double price = obj.optDouble("close", 0);
            double open = obj.optDouble("open", price);
            double high = obj.optDouble("high", price);
            double risk = Math.random() * 3;

            list.add(new Investment(c, obj.optString("name", c), price, risk, open, high));
        }

        return list;
    }
}
