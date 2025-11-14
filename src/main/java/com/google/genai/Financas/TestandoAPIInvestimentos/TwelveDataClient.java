package com.google.genai.Financas.TestandoAPIInvestimentos;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class TwelveDataClient extends ApiClient {


    private static final String API_KEY = "6f58c2f5d2194c5ba87cd8c18b6a8599";

    public TwelveDataClient() {
        super("https://api.twelvedata.com");
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

    public List<Investment> getStocks() {
        List<Investment> list = new ArrayList<>();
        String[] symbols = {"AAPL", "MSFT", "GOOGL", "AMZN", "NVDA", "TSLA", "NFLX", "V", "JPM"};

        for (String sym : symbols) {
            String json = get("/quote?symbol=" + sym + "&apikey=" + API_KEY);
            JSONObject obj = new JSONObject(json);

            if (obj.optString("status", "").equalsIgnoreCase("error") || obj.has("code")) {
                continue;
            }

            double price = obj.optDouble("close", 0);
            double open = obj.optDouble("open", price);
            double high = obj.optDouble("high", price);

            double risk = getGuaranteedRisk(list.size());

            list.add(new Investment(sym, obj.optString("name", sym), price, risk, open, high));
        }

        return list;
    }

    public List<Investment> getCryptos() {
        List<Investment> list = new ArrayList<>();
        String[] cryptos = {"BTC/USD", "ETH/USD", "SOL/USD", "XRP/USD", "ADA/USD", "DOGE/USD", "DOT/USD", "LINK/USD", "AVAX/USD"};

        for (String c : cryptos) {
            String json = get("/quote?symbol=" + c + "&apikey=" + API_KEY);
            JSONObject obj = new JSONObject(json);

            if (obj.optString("status", "").equalsIgnoreCase("error") || obj.has("code")) {
                continue;
            }

            double price = obj.optDouble("close", 0);
            double open = obj.optDouble("open", price);
            double high = obj.optDouble("high", price);

            double risk = getGuaranteedRisk(list.size());

            list.add(new Investment(c, c, price, risk, open, high));
        }

        return list;
    }
}