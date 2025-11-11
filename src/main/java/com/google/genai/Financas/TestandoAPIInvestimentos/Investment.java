package com.google.genai.Financas.TestandoAPIInvestimentos;

public record Investment(
        String symbol,
        String name,
        double price,
        double risk,
        double open,
        double high,
        double rendimentoMensal
) {
    @Override
    public String toString() {
        return symbol + " - " + name +
                "\nPreço atual: R$" + String.format("%.2f", price) +
                " | Abertura: R$" + String.format("%.2f", open) +
                " | Máximo: R$" + String.format("%.2f", high) +
                "\nRisco: " + String.format("%.2f", risk) + "%" +
                " | Rendimento mensal: " + String.format("%.2f", rendimentoMensal) + "%\n";
    }
}