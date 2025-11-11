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
 * Cliente da API Twelve Data (mercado internacional e criptoativos).
 * Obtém informações reais de ações e criptomoedas e calcula risco com base na volatilidade.
 */
public class TwelveDataClient {
    private static final String API_KEY = "ec28f4ddd7a24232bdc2c8889722ed19";
    private static final String BASE_URL = "https://api.twelvedata.com/";
    private static final String INTERVAL_DAILY = "1day";
    private static final int OUTPUT_SIZE_MONTH = 25;

    private static final String[] STOCK_SYMBOLS = {"MSFT", "AAPL", "GOOGL", "AMZN"};
    private static final String[] CRYPTO_SYMBOLS = {
            "BTC/USD", "ETH/USD", "BNB/USD", "ADA/USD",
            "SOL/USD", "DOT/USD", "DOGE/USD", "SHIB/USD",
            "MATIC/USD", "LTC/USD"
    };

    private static final long API_PAUSE_MS = 3000;

    // ==========================================================
    // 📈 MÉTODO: Ações internacionais
    // ==========================================================
    public static List<Investment> getStocks() {
        List<Investment> list = new ArrayList<>();

        try {
            for (String symbol : STOCK_SYMBOLS) {
                String quoteURL = BASE_URL + "quote?symbol=" + symbol + "&apikey=" + API_KEY;
                JSONObject quoteResponse = new JSONObject(readURL(quoteURL, API_PAUSE_MS));

                if (quoteResponse.optString("status").equals("error") || quoteResponse.optDouble("open", 0) == 0)
                    continue;

                String name = quoteResponse.optString("name", symbol);
                double open = quoteResponse.optDouble("open", 0);
                double high = quoteResponse.optDouble("high", 0);
                double low = quoteResponse.optDouble("low", high);
                double price = quoteResponse.optDouble("close", 0);
                double changePercent = quoteResponse.optDouble("percent_change", 0);

                // Histórico de 30 dias
                String seriesURL = BASE_URL + "time_series?symbol=" + symbol + "&interval=" + INTERVAL_DAILY
                        + "&outputsize=" + OUTPUT_SIZE_MONTH + "&apikey=" + API_KEY;

                JSONObject seriesResponse = new JSONObject(readURL(seriesURL, API_PAUSE_MS));
                JSONArray timeSeries = seriesResponse.optJSONArray("values");
                double price30DaysAgo = 0.0;

                if (timeSeries != null && timeSeries.length() >= OUTPUT_SIZE_MONTH) {
                    JSONObject historicalData = timeSeries.getJSONObject(timeSeries.length() - 1);
                    price30DaysAgo = historicalData.optDouble("close", 0);
                }

                // 💡 Cálculo real de risco com base na volatilidade
                float risk = (float) ((high - low) / (price == 0 ? 1 : price));
                double volatility = Math.abs(changePercent) / 4;

                // 🔗 Link seguro (Yahoo Finance)
                String url = "https://finance.yahoo.com/quote/" + symbol;

                // Adiciona o investimento completo
                list.add(new Investment(
                        symbol,
                        name,
                        risk,
                        open,
                        high,
                        low,
                        price,
                        changePercent,
                        volatility,
                        "TwelveData (Internacional)",
                        "USD",
                        url
                ));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar investimentos Twelve Data (Ações): " + e.getMessage());
        }

        return list;
    }

    // ==========================================================
    // 💰 MÉTODO: Criptomoedas
    // ==========================================================
    public static List<Investment> getCryptos() {
        List<Investment> list = new ArrayList<>();

        try {
            for (String symbol : CRYPTO_SYMBOLS) {
                String cleanSymbol = symbol.replace("/USD", "");

                String quoteURL = BASE_URL + "quote?symbol=" + symbol + "&apikey=" + API_KEY;
                JSONObject quoteResponse = new JSONObject(readURL(quoteURL, API_PAUSE_MS));

                if (quoteResponse.optString("status").equals("error") || quoteResponse.optDouble("open", 0) == 0)
                    continue;

                String name = quoteResponse.optString("name", cleanSymbol);
                double open = quoteResponse.optDouble("open", 0);
                double high = quoteResponse.optDouble("high", 0);
                double low = quoteResponse.optDouble("low", high);
                double price = quoteResponse.optDouble("close", 0);
                double changePercent = quoteResponse.optDouble("percent_change", 0);

                // Histórico de 30 dias
                String seriesURL = BASE_URL + "time_series?symbol=" + symbol + "&interval=" + INTERVAL_DAILY
                        + "&outputsize=" + OUTPUT_SIZE_MONTH + "&apikey=" + API_KEY;

                JSONObject seriesResponse = new JSONObject(readURL(seriesURL, API_PAUSE_MS));
                JSONArray timeSeries = seriesResponse.optJSONArray("values");
                double price30DaysAgo = 0.0;

                if (timeSeries != null && timeSeries.length() >= OUTPUT_SIZE_MONTH) {
                    JSONObject historicalData = timeSeries.getJSONObject(timeSeries.length() - 1);
                    price30DaysAgo = historicalData.optDouble("close", 0);
                }

                // 💡 Risco real baseado na volatilidade (quanto mais varia, mais arriscado)
                float risk = (float) ((high - low) / (price == 0 ? 1 : price));
                double volatility = Math.abs(changePercent) / 3;

                // 🔗 Link oficial e seguro — pode usar CoinMarketCap ou Binance
                String url = "https://coinmarketcap.com/currencies/" + cleanSymbol.toLowerCase() + "/";

                list.add(new Investment(
                        cleanSymbol,
                        name,
                        risk,
                        open,
                        high,
                        low,
                        price,
                        changePercent,
                        volatility,
                        "TwelveData (Cripto)",
                        "USD",
                        url
                ));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar investimentos Twelve Data (Cripto): " + e.getMessage());
        }

        return list;
    }

    // ==========================================================
    // 🔍 Utilitário para leitura da API
    // ==========================================================
    private static String readURL(String urlStr, long sleepTime) throws Exception {
        Thread.sleep(sleepTime);

        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Java TwelveData Client");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
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