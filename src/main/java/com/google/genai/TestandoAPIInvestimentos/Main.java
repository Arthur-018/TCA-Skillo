package com.google.genai.TestandoAPIInvestimentos;

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
            System.out.println("1️⃣  Perfil Iniciante (baixo risco: até 1.0)");
            System.out.println("2️⃣  Perfil Experiente (alto risco: acima de 1.0)");
            System.out.println("3️⃣  Sair");
            System.out.print("👉  Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1 -> mostrarInvestimentos(true);
                case 2 -> mostrarInvestimentos(false);
                case 3 -> {
                    System.out.println("👋 Encerrando...");
                    executando = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }

    private static void mostrarInvestimentos(boolean iniciante) {
        String perfil = iniciante ? "🟢 PERFIL INICIANTE" : "🔴 PERFIL EXPERIENTE";
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println(perfil);
        System.out.println("═══════════════════════════════════════════════");

        System.out.println("Aguarde... Buscando dados de ativos Nacionais (B3) e Internacionais.");

        List<Investment> nacional = BrapiClient.getInvestments();

        List<Investment> internacional = TwelveDataClient.getInvestments();


        List<Investment> filteredNacional = nacional.stream()
                .filter(inv -> iniciante ? inv.risk() < 1.0f : inv.risk() >= 1.0f)
                .collect(Collectors.toList());

        List<Investment> filteredInternacional = internacional.stream()
                .filter(inv -> iniciante ? inv.risk() < 1.0f : inv.risk() >= 1.0f)
                .collect(Collectors.toList());


        System.out.println("\n🇧🇷 AÇÕES NACIONAIS (Via Brapi B3 - Top 5):");
        for (Investment inv : filteredNacional.stream().limit(5).toList()) {
            exibirInvestimento(inv);
        }

        System.out.println("\n🌍 AÇÕES INTERNACIONAIS (Via Twelve Data - Top 5):");
        for (Investment inv : filteredInternacional.stream().limit(5).toList()) {
            exibirInvestimento(inv);
        }

        if (filteredNacional.isEmpty() && filteredInternacional.isEmpty()) {
            System.out.println("⚠️ Não foram encontrados investimentos que se enquadrem no seu perfil de risco (Risco: " + (iniciante ? "< 1.0" : ">= 1.0") + ") com os dados atuais.");
        }
    }

    private static void exibirInvestimento(Investment inv) {
        double rendimentoDiario = ((inv.price() - inv.open()) / inv.open()) * 100;

        double rendimentoMensal = 0.0;
        if (inv.price30DaysAgo() > 0) {
            rendimentoMensal = ((inv.price() - inv.price30DaysAgo()) / inv.price30DaysAgo()) * 100;
        }

        String rendimentoMensalDisplay = rendimentoMensal != 0.0 ? String.format("%.2f%%", rendimentoMensal) : "Dados Históricos Indisponíveis";


        System.out.printf("""
                📊 %s - %s
                • 💰 Abertura: R$ %.2f
                • 📈 Máxima: R$ %.2f
                • 💹 Atual: R$ %.2f
                • ⚖️ Risco: %.2f
                • 📆 Rendimento diário: %.2f%%
                • 📈 Rendimento mensal real: %s
                • 🔗 Link oficial: %s
                ────────────────────────────────────────────────
                """,
                inv.symbol(), inv.name(),
                inv.open(), inv.high(), inv.price(),
                inv.risk(), rendimentoDiario, rendimentoMensalDisplay, inv.url());
    }
}