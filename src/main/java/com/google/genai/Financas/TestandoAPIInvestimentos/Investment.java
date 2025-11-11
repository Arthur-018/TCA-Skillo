package com.google.genai.Financas.TestandoAPIInvestimentos;


public record Investment(
        String symbol,            // Código do ativo (ex: PETR4, AAPL)
        String name,              // Nome da empresa/ativo
        float risk,               // Risco calculado pela volatilidade (não aleatório)
        double open,              // Valor de abertura do ativo
        double high,              // Valor máximo recente
        double low,               // Valor mínimo recente
        double price,             // Preço atual
        double changePercent,     // Variação percentual (diária ou mensal)
        double volatility,        // Volatilidade anual ou do período
        String source,            // Origem dos dados (B3, BRAPI, TWELVEDATA, etc.)
        String currency,          // Moeda (BRL, USD)
        String url                // Link oficial para investir/ver detalhes
) {

    @Override
    public String toString() {
        return """
                📈 %s - %s
                💰 Preço atual: %.2f %s
                📊 Risco: %.2f | Variação: %.2f%%
                🔗 Link: %s
                """.formatted(symbol, name, price, currency, risk, changePercent, url);
    }

    public double price30DaysAgo() {
        return 0;
    }
}