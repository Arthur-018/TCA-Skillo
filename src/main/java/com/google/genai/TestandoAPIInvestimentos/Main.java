package com.google.genai.TestandoAPIInvestimentos;



import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n═══════════════════════════════════════════════");
            System.out.println("💼  SISTEMA DE INVESTIMENTOS INTELIGENTES");
            System.out.println("═══════════════════════════════════════════════");
            System.out.println("1️⃣  Perfil Iniciante (baixo risco)");
            System.out.println("2️⃣  Perfil Experiente (alto risco)");
            System.out.println("3️⃣  Sair");
            System.out.print("👉  Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1 -> mostrarInvestimentos(true);
                case 2 -> mostrarInvestimentos(false);
                case 3 -> {
                    System.out.println("👋 Encerrando...");
                    executando = false;
                }
                default -> System.out.println("❌ Opção inválida.");
            }
        }
        sc.close();
    }

    private static void mostrarInvestimentos(boolean iniciante) {
        String perfil = iniciante ? "🟢 PERFIL INICIANTE" : "🔴 PERFIL EXPERIENTE";
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println(perfil);
        System.out.println("═══════════════════════════════════════════════");

        System.out.println("\n🇧🇷 AÇÕES NACIONAIS:");
        for (Investment inv : BrapiClient.getInvestments(iniciante)) {
            exibirInvestimento(inv);
        }

        System.out.println("\n🌍 AÇÕES INTERNACIONAIS:");
        for (Investment inv : EODHDClient.getInvestments(iniciante)) {
            exibirInvestimento(inv);
        }
    }

    private static void exibirInvestimento(Investment inv) {
        double rendimento = ((inv.price() - inv.open()) / inv.open()) * 100;
        System.out.printf("""
                📊 %s - %s
                • 💰 Abertura: R$ %.2f
                • 📈 Máxima: R$ %.2f
                • 💹 Atual: R$ %.2f
                • ⚖️ Risco: %.2f
                • 📆 Rendimento mensal estimado: %.2f%%
                • 🔗 Link oficial: %s
                ────────────────────────────────────────────────
                """,
                inv.symbol(), inv.name(),
                inv.open(), inv.high(), inv.price(),
                inv.risk(), rendimento, inv.url());
    }
}