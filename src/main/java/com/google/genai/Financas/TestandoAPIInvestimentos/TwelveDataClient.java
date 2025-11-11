package com.google.genai.Financas.TestandoAPIInvestimentos;

import com.google.genai.Financas.TestandoAPIInvestimentos.Investment;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TwelveDataClient extends com.google.genai.Financas.API.ApiClient {

    private static final String API_KEY = "demo"; // Troque pela sua chave real

    public TwelveDataClient() {
        super("https://api.twelvedata.com");
    }

    public List<Investment> getStocks() {
        List<Investment> list = new ArrayList<>();
        String[] symbols = {"AAPL", "MSFT", "GOOGL"};

        for (String sym : symbols) {
            String json = get("/quote?symbol=" + sym + "&apikey=" + API_KEY);
            JSONObject obj = new JSONObject(json);

            double price = obj.optDouble("price", 0);
            double open = obj.optDouble("open", 0);
            double high = obj.optDouble("high", 0);
            double rendimentoMensal = price > 0 && open > 0 ? ((price - open) / open) * 100 : 0;
            double risk = Math.random() * 3;

            list.add(new Investment(sym, obj.optString("name", sym), price, risk, open, high, rendimentoMensal));
        }

        return list;
    }

    public List<Investment> getCryptos() {
        List<Investment> list = new ArrayList<>();
        String[] cryptos = {"BTC/USD", "ETH/USD", "SOL/USD"};

        for (String c : cryptos) {
            String json = get("/quote?symbol=" + c + "&apikey=" + API_KEY);
            JSONObject obj = new JSONObject(json);

            double price = obj.optDouble("price", 0);
            double open = obj.optDouble("open", 0);
            double high = obj.optDouble("high", 0);
            double rendimentoMensal = price > 0 && open > 0 ? ((price - open) / open) * 100 : 0;
            double risk = Math.random() * 3;

            list.add(new Investment(c, obj.optString("name", c), price, risk, open, high, rendimentoMensal));
        }

        return list;
    }
}