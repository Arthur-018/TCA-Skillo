package com.google.genai.TestandoAPIInvestimentos;


public record Investment(
        String symbol,
        String name,
        float risk,
        double open,
        double high,
        double price,
        double price30DaysAgo, //MUITO importante para calcular o rendimento mensal NÂO MUDE, SÓ SE NECESSÀRIO
        String url
) {
    @Override
    public String toString() {
        return "%s - %s | Risco: %.2f".formatted(symbol, name, risk);
    }
}