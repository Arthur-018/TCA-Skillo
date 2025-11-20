package com.google.genai.Financas.TestandoAPIInvestimentos;

import com.google.genai.Financas.Modelos.Investment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class BrapiClient extends ApiClient {

    private static final String BRA_API_KEY = "27tkQF88tocKRJNxKzRjtm";

    public BrapiClient() {
        super("https://brapi.dev/api");
    }
    // Método para tentar simular um preço de abertura baseado no fechamento
    private double simularAbertura(double precoFechamento) {

        // Gera uma variação bem pequena, entre 0.995 e 1.005.
        double variacao = 0.995 + (Math.random() * 0.01);

        // Retorna o preço de abertura baseado no fechamento vezes essa variação
        return precoFechamento * variacao;
    }
    // método que calcula um high garantido
    private double calcularHighGarantido(double precoAbertura, double precoFechamento, int index) {

        // Os riscos são manuais.
        // Os primeiros são risco baixo (0.25), depois risco médio (0.75) e depois risco maior (1.50)
        double[] targetRisks = {
                0.25, 0.25, 0.25, 0.25, 0.25,
                0.75, 0.75, 0.75, 0.75, 0.75,
                1.50, 1.50, 1.50, 1.50, 1.50
        };

        int cycleLength = targetRisks.length;
        int bandIndex = index % cycleLength;

        double targetRiskPercent = targetRisks[bandIndex];

        double low = Math.min(precoAbertura, precoFechamento);

        double requiredHigh = ((targetRiskPercent / 100.0) * precoAbertura) + low;

        return requiredHigh;
    }

    // Cálculo de Risco (Volatilidade Diária)
    private double calcularVolatilidade(double open, double high, double price) {
        double low = Math.min(open, price);

        if (open <= 0) return 0.0;

        return ((high - low) / open) * 100.0;
    }

    // Ações Nacionais (B3)
    public List<Investment> getNationalStocks() {
        List<Investment> list = new ArrayList<>();

        String endpoint = "/quote/list?limit=30&token=" + BRA_API_KEY;
        String jsonStr = get(endpoint);

        JSONObject obj = new JSONObject(jsonStr);

        JSONArray stocks = obj.optJSONArray("stocks");
        if (stocks == null) return list;

        for (int i = 0; i < stocks.length(); i++) {
            JSONObject s = stocks.getJSONObject(i);

            String symbol = s.optString("stock");
            String name = s.optString("name");
            double price = s.optDouble("close", 0);

            double open = simularAbertura(price);

            double high = calcularHighGarantido(open, price, list.size());

            double risk = calcularVolatilidade(open, high, price);

            list.add(new Investment(symbol, name, price, risk, open, high));
        }

        return list;
    }

    // Ações Internacionais
    public List<Investment> getInternationalStocks() {
        List<Investment> list = new ArrayList<>();
        String[] symbols = {
                "AAPL", "MSFT", "GOOGL", "AMZN", "NVDA", "TSLA", "NFLX", "V", "JPM",
                "MCD", "NKE", "KO", "PEP", "WMT", "DIS", "BAC", "PFE", "MRK"
        };

        for (String sym : symbols) {
            String endpoint = "/quote/" + sym + "?token=" + BRA_API_KEY + "&country=us";
            String json = get(endpoint);
            JSONObject obj = new JSONObject(json);

            JSONArray results = obj.optJSONArray("results");
            if (results == null || results.length() == 0) continue;

            JSONObject s = results.getJSONObject(0);

            String symbol = s.optString("symbol");
            String name = s.optString("longName", sym);
            double price = s.optDouble("regularMarketPrice", 0);

            double open = simularAbertura(price);

            double high = calcularHighGarantido(open, price, list.size());

            double risk = calcularVolatilidade(open, high, price);

            list.add(new Investment(symbol, name, price, risk, open, high));
        }

        return list;
    }
}