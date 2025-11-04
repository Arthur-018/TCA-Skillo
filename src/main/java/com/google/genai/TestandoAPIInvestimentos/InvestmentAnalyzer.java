package com.google.genai.TestandoAPIInvestimentos;

import java.util.*;

public class InvestmentAnalyzer {

    // Calcula o risco
    public static float calcularRisco(List<Float> prices) {
        if (prices.size() < 2) return 0f;

        float soma = 0f;
        for (float p : prices) soma += p;
        float media = soma / prices.size();

        float variancia = 0f;
        for (float p : prices) variancia += Math.pow(p - media, 2);
        variancia /= prices.size();

        return (float) Math.sqrt(variancia);
    }

    // Classifica com base no risco
    public static String classificarInvestimento(float risco) {
        if (risco < 1.0f) return "Baixo risco (ideal para iniciantes)";
        if (risco < 2.0f) return "Médio risco";
        return "Alto risco (para experientes)";
    }
}

