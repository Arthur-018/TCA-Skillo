package com.google.genai.Financas.TestandoAPIInvestimentos;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Cliente da API Brapi (B3 - Brasil).
 * Obtém informações reais das ações brasileiras e calcula risco com base na volatilidade.
 */
public class BrapiClient {
    private static final String TOKEN = "4KN8PE9RqgVAxzxjRuRTTP";
    private static final String QUOTE_URL = "https://brapi.dev/api/quote/";
    private static final String[] STOCKS = {
            "VALE3", "PETR4", "ITUB4", "BBDC4", "MGLU3", "LREN3", "B3SA3", "ABEV3",
            "BPAC11", "RADL3", "RENT3", "WEGE3", "SUZB3", "PRIO3", "BBAS3",
            "HAPV3", "RDOR3", "PETZ3"
    };
    private static final long API_PAUSE_MS = 50;

    public static List<Investment> getInvestments() {
        List<Investment> list = new ArrayList<>();

        try {
            for (String symbol : STOCKS) {
                String urlStr = QUOTE_URL + symbol + "?token=" + TOKEN;
                JSONObject response = new JSONObject(readURL(urlStr, API_PAUSE_MS));
                JSONArray stocksArray = response.optJSONArray("results");

                if (stocksArray == null || stocksArray.isEmpty()) {
                    System.err.println("❌ Brapi: Não foi possível obter resultados para " + symbol + ". Pulando...");
                    continue;
                }

                JSONObject stockData = stocksArray.getJSONObject(0);

                String name = stockData.optString("longName", symbol);
                double open = stockData.optDouble("regularMarketOpen", 0);
                double high = stockData.optDouble("regularMarketDayHigh", 0);
                double low = stockData.optDouble("regularMarketDayLow", high);
                double price = stockData.optDouble("regularMarketPrice", 0);
                double price30DaysAgo = stockData.optDouble("regularMarketPreviousClose", 0);
                double changePercent = stockData.optDouble("regularMarketChangePercent", 0);

                // 💡 Cálculo real de risco com base na volatilidade (diferença entre máximo e mínimo / preço)
                float risk = 0.1f;
                if (price > 0 && high > 0 && low > 0) {
                    risk = (float) ((high - low) / price);
                }

                // Volatilidade mensal simples (poderia vir de histórico, aqui é um cálculo base)
                double volatility = Math.abs(changePercent) / 5;

                // 🔗 Link oficial e seguro para ver detalhes e investir
                String officialLink = "https://www.infomoney.com.br/cotacoes/" + symbol + "-brasil/";

                // Criar Investment com todos os dados reais
                Investment inv = new Investment(
                        symbol,
                        name,
                        risk,
                        open,
                        high,
                        low,
                        price,
                        changePercent,
                        volatility,
                        "Brapi (B3)",
                        "BRL",
                        officialLink
                );

                list.add(inv);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar investimentos Brapi (B3): " + e.getMessage());
        }

        return list;
    }

    /**
     * Lê o conteúdo JSON da URL especificada com pausa entre as requisições.
     */
    private static String readURL(String urlStr, long sleepTime) throws Exception {
        Thread.sleep(sleepTime);

        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorSb = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) errorSb.append(errorLine);
                System.err.println("Resposta de Erro da API: " + errorSb);
            }
            throw new Exception("Falha na requisição. Código de resposta: " + responseCode);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }
}