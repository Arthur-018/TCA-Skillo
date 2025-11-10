package com.google.genai.Financas.TestandoAPIInvestimentos;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n═══════════════════════════════════════════════");
            System.out.println("  SISTEMA DE INVESTIMENTOS INTELIGENTES");
            System.out.println("═══════════════════════════════════════════════");
            System.out.println("Escolha o tipo de Ativo:");
            System.out.println("1️⃣  Ações (Nacional e Internacional)");
            System.out.println("2️⃣  Criptomoedas");
            System.out.println("3️⃣  Sair");
            System.out.print("👉  Escolha uma opção: ");

            int opcaoAtivo;
            try {
                opcaoAtivo = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Digite apenas números.");
                continue;
            }

            switch (opcaoAtivo) {
                case 1 -> mostrarMenuAcoes(sc);
                case 2 -> mostrarMenuCryptos();
                case 3 -> {
                    System.out.println("👋 Encerrando...");
                    executando = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }

    private static void mostrarMenuAcoes(Scanner sc) {
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  SELECIONE O PERFIL DE RISCO (AÇÕES)");
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("1️⃣  Perfil Iniciante (risco: até 2.0)");
        System.out.println("2️⃣  Perfil Intermediário (risco: 2.0 até 3.0)");
        System.out.println("3️⃣  Perfil Experiente (risco: acima de 3.0)");
        System.out.print("👉  Escolha uma opção: ");

        int opcaoRisco;
        try {
            opcaoRisco = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(" Digite apenas números.");
            return;
        }

        if (opcaoRisco >= 1 && opcaoRisco <= 3) {
            mostrarInvestimentos(opcaoRisco, "ACAO");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void mostrarMenuCryptos() {
        mostrarInvestimentos(0, "CRYPTO");
    }

    private static void mostrarInvestimentos(int perfil, String tipoAtivo) {
        String perfilNome;
        float minRisk;
        float maxRisk;

        if (tipoAtivo.equals("ACAO")) {
            switch (perfil) {
                case 1 -> {
                    perfilNome = "🟢 PERFIL INICIANTE (AÇÕES)";
                    minRisk = 0.0f;
                    maxRisk = 2.0f;
                }
                case 2 -> {
                    perfilNome = "🟡 PERFIL INTERMEDIÁRIO (AÇÕES)";
                    minRisk = 2.0f;
                    maxRisk = 3.001f;
                }
                case 3 -> {
                    perfilNome = "🔴 PERFIL EXPERIENTE (AÇÕES)";
                    minRisk = 3.001f;
                    maxRisk = Float.MAX_VALUE;
                }
                default -> {
                    perfilNome = "PERFIL DESCONHECIDO";
                    minRisk = 0.0f;
                    maxRisk = Float.MAX_VALUE;
                }
            }

            System.out.println("\n═══════════════════════════════════════════════");
            System.out.println(perfilNome);
            System.out.println("═══════════════════════════════════════════════");
            System.out.println("Aguarde... Buscando dados de ativos Nacionais (B3) e Internacionais.");

            List<Investment> nacional = com.google.genai.TestandoAPIInvestimentos.BrapiClient.getInvestments();
            List<Investment> internacional = TwelveDataClient.getStocks();

            List<Investment> filteredNacional = nacional.stream()
                    .filter(inv -> inv.risk() >= minRisk && inv.risk() < maxRisk)
                    .collect(Collectors.toList());

            List<Investment> filteredInternacional = internacional.stream()
                    .filter(inv -> inv.risk() >= minRisk && inv.risk() < maxRisk)
                    .collect(Collectors.toList());

            System.out.println("\n🇧🇷 AÇÕES NACIONAIS (Top 5):");
            for (Investment inv : filteredNacional.stream().limit(5).toList()) {
                exibirInvestimento(inv, "BRL");
            }

            System.out.println("\n🌍 AÇÕES INTERNACIONAIS (Top 5):");
            for (Investment inv : filteredInternacional.stream().limit(5).toList()) {
                exibirInvestimento(inv, "USD");
            }

            if (filteredNacional.isEmpty() && filteredInternacional.isEmpty()) {
                System.out.println("⚠️ Não foram encontrados investimentos que se enquadrem no seu perfil de risco com os dados atuais.");
            }

        } else if (tipoAtivo.equals("CRYPTO")) {

            System.out.println("\n═══════════════════════════════════════════════");
            System.out.println("💰 CRIPTOATIVOS - RISCO E VOLATILIDADE");
            System.out.println("═══════════════════════════════════════════════");
            System.out.println("Aguarde... Buscando 10 Criptomoedas (pode levar até 100 segundos devido ao limite da API).");

            List<Investment> cryptos = TwelveDataClient.getCryptos();

            List<Investment> sortedCryptos = cryptos.stream()
                    .sorted(Comparator.comparing(Investment::risk))
                    .collect(Collectors.toList());

            if (sortedCryptos.size() < 10) {
                System.out.println("⚠️ Apenas " + sortedCryptos.size() + " criptomoedas foram carregadas. O limite da API pode ter sido atingido.");
            }

            List<Investment> lowestRisk = sortedCryptos.stream().limit(5).collect(Collectors.toList());

            int skipCount = Math.max(0, sortedCryptos.size() - 5);
            List<Investment> highestRisk = sortedCryptos.stream().skip(skipCount).collect(Collectors.toList());


            System.out.println("\n✅ 5 CRIPTOMOEDAS DE MENOR RISCO:");
            for (Investment inv : lowestRisk) {
                exibirInvestimento(inv, "USD");
            }

            System.out.println("\n🔥 5 CRIPTOMOEDAS DE MAIOR RISCO:");
            for (Investment inv : highestRisk) {
                exibirInvestimento(inv, "USD");
            }

            if (sortedCryptos.isEmpty()) {
                System.out.println("⚠️ Não foi possível carregar as criptomoedas. A API pode estar congestionada ou o limite de requisições foi atingido.");
            }
        }
    }

    private static void exibirInvestimento(Investment inv, String currency) {
        double rendimentoDiario = ((inv.price() - inv.open()) / inv.open()) * 100;

        double rendimentoMensal = 0.0;
        if (inv.price30DaysAgo() > 0) {
            rendimentoMensal = ((inv.price() - inv.price30DaysAgo()) / inv.price30DaysAgo()) * 100;
        }

        String priceDisplay = (currency.equals("BRL")) ? String.format("R$ %.2f", inv.price()) : String.format("US$ %.2f", inv.price());
        String openDisplay = (currency.equals("BRL")) ? String.format("R$ %.2f", inv.open()) : String.format("US$ %.2f", inv.open());
        String highDisplay = (currency.equals("BRL")) ? String.format("R$ %.2f", inv.high()) : String.format("US$ %.2f", inv.high());
        String riskDisplay = (currency.equals("BRL")) ? String.format("%.2f", inv.risk()) : String.format("%.2f (Volatilidade)", inv.risk());

        String rendimentoMensalDisplay = rendimentoMensal != 0.0 ? String.format("%.2f%%", rendimentoMensal) : "Dados Históricos Indisponíveis";


        System.out.printf("""
                📊 %s - %s
                • 💰 Abertura: %s
                • 📈 Máxima: %s
                • 💹 Atual: %s
                • ⚖️ Risco: %s
                • 📆 Rendimento diário: %.2f%%
                • 📈 Rendimento mensal real: %s
                • 🔗 Link oficial: %s
                ────────────────────────────────────────────────
                """,
                inv.symbol(), inv.name(),
                openDisplay, highDisplay, priceDisplay,
                riskDisplay, rendimentoDiario, rendimentoMensalDisplay, inv.url());
    }
}