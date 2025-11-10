package com.google.genai.Financas.TestandoAPIInvestimentos;


public record Investment(
        String symbol,
        String name,
        float risk,
        double open,
        double high,
        double price,
        double price30DaysAgo,
        String url
) {
    @Override
    public String toString() {
        return "%s - %s | Risco: %.2f".formatted(symbol, name, risk);
    }
}