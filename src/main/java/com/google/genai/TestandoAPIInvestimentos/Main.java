package com.google.genai.TestandoAPIInvestimentos;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU DE INVESTIMENTOS ===");
            System.out.println("1 - Perfil Iniciante (baixo risco)");
            System.out.println("2 - Perfil Experiente (médio e alto risco)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            if (opcao == 0) break;

            String perfil = (opcao == 1) ? "INICIANTE" : "EXPERIENTE";
            System.out.println("\n🟢 Perfil selecionado: " + perfil);
            System.out.println("Buscando dados na Alpha Vantage...\n");


            String[] acoes = {"PETR4.SA", "VALE3.SA", "ITUB4.SA", "BBAS3.SA"};

            for (String acao : acoes) {
                try {
                    List<Float> prices = AlphaVantageClient.getDailyPrices(acao);
                    if (prices.isEmpty()) continue;

                    float risco = InvestmentAnalyzer.calcularRisco(prices);
                    String classificacao = InvestmentAnalyzer.classificarInvestimento(risco);


                    if (opcao == 1 && risco < 1.0f) {
                        System.out.printf(" %s | Risco: %.4f | %s\n", acao, risco, classificacao);
                    } else if (opcao == 2 && risco >= 1.0f) {
                        System.out.printf("⚡ %s | Risco: %.4f | %s\n", acao, risco, classificacao);
                    }

                } catch (Exception e) {
                    System.out.println(" Erro ao processar " + acao + ": " + e.getMessage());
                }
            }

            System.out.println("\n--------------------------------------");
        }

        System.out.println("Programa encerrado. 👋");
        sc.close();
    }
}
