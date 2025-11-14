package com.google.genai.Financas.Principal;

import com.google.genai.Financas.TestandoAPIInvestimentos.BrapiClient;
import com.google.genai.Financas.TestandoAPIInvestimentos.TwelveDataClient;
import com.google.genai.Financas.TestandoAPIInvestimentos.Investment;

import java.util.List;
import java.util.Scanner;

public class PerfilFinanceiro {

    public void pefilFinanceiro() {
        Scanner scan = new Scanner(System.in);
        int pontos = 0;

        System.out.println("=== SIMULADOR DE PERFIL DE INVESTIDOR ===");
        System.out.println("Responda as perguntas abaixo:\n");

        System.out.println("1️⃣ Qual é o seu conhecimento sobre investimentos?");
        System.out.println("(a) Nenhum conhecimento");
        System.out.println("(b) Conhecimento básico");
        System.out.println("(c) Experiência avançada");
        String resposta = scan.nextLine();
        if (resposta.equalsIgnoreCase("a")) pontos += 1;
        else if (resposta.equalsIgnoreCase("b")) pontos += 2;
        else if (resposta.equalsIgnoreCase("c")) pontos += 3;

        System.out.println("\n2️⃣ Como você reagiria a uma queda de 20% nos seus investimentos?");
        System.out.println("(a) Venderia tudo");
        System.out.println("(b) Esperaria recuperar");
        System.out.println("(c) Aumentaria meus aportes");
        resposta = scan.nextLine();
        if (resposta.equalsIgnoreCase("a")) pontos += 1;
        else if (resposta.equalsIgnoreCase("b")) pontos += 2;
        else if (resposta.equalsIgnoreCase("c")) pontos += 3;

        System.out.println("\n3️⃣ Qual é o seu objetivo de investimento?");
        System.out.println("(a) Preservação de capital");
        System.out.println("(b) Crescimento moderado");
        System.out.println("(c) Alto crescimento (alto risco)");
        resposta = scan.nextLine();
        if (resposta.equalsIgnoreCase("a")) pontos += 1;
        else if (resposta.equalsIgnoreCase("b")) pontos += 2;
        else if (resposta.equalsIgnoreCase("c")) pontos += 3;

        System.out.println("\n4️⃣ Qual horizonte de tempo você planeja manter seus investimentos?");
        System.out.println("(a) Curto prazo (menos de 1 ano)");
        System.out.println("(b) Médio prazo (1 a 5 anos)");
        System.out.println("(c) Longo prazo (mais de 5 anos)");
        resposta = scan.nextLine();
        if (resposta.equalsIgnoreCase("a")) pontos += 1;
        else if (resposta.equalsIgnoreCase("b")) pontos += 2;
        else if (resposta.equalsIgnoreCase("c")) pontos += 3;

        String perfil = "";
        if (pontos <= 7) perfil = "Conservador";
        else if (pontos <= 11) perfil = "Intermediário";
        else perfil = "Experiente";

        System.out.println("\nSeu perfil de investidor é: " + perfil.toUpperCase());

        mostrarInvestimentos(perfil);
        scan.close();
    }

    private void mostrarInvestimentos(String perfil) {
        BrapiClient brapi = new BrapiClient();
        TwelveDataClient twelve = new TwelveDataClient();

        System.out.println("\n🇧🇷 AÇÕES NACIONAIS (Recomendadas para " + perfil.toUpperCase() + "):");
        List<Investment> brapiInvestments = brapi.getInvestments();
        List<Investment> filteredBrapi = filtrarInvestimentos(brapiInvestments, perfil);

        if (filteredBrapi.isEmpty()) {
            System.out.println("Nenhuma ação disponível para o perfil " + perfil.toUpperCase() + " que se enquadre nos critérios de risco.");
        } else {
            for (Investment i : filteredBrapi) System.out.println(i);
        }

        System.out.println("\n🌍 AÇÕES INTERNACIONAIS (Recomendadas para " + perfil.toUpperCase() + "):");
        List<Investment> internationalStocks = twelve.getStocks();
        List<Investment> filteredInternational = filtrarInvestimentos(internationalStocks, perfil);

        if (filteredInternational.isEmpty()) {
            System.out.println("Nenhuma ação disponível para o perfil " + perfil.toUpperCase() + " que se enquadre nos critérios de risco.");
        } else {
            for (Investment i : filteredInternational) System.out.println(i);
        }

        System.out.println("\n💎 CRIPTOMOEDAS (Recomendadas para " + perfil.toUpperCase() + "):");
        List<Investment> cryptos = twelve.getCryptos();
        List<Investment> filteredCryptos = filtrarInvestimentos(cryptos, perfil);

        if (filteredCryptos.isEmpty()) {
            System.out.println("Nenhuma criptomoeda disponível para o perfil " + perfil.toUpperCase() + " que se enquadre nos critérios de risco.");
        } else {
            for (Investment i : filteredCryptos) System.out.println(i);
        }
    }


    private List<Investment> filtrarInvestimentos(List<Investment> investimentos, String perfil) {
        double maxRisk = Double.MAX_VALUE;
        double minRisk = 0.0;

        if (perfil.equalsIgnoreCase("Conservador")) {
            maxRisk = 1.00;
        } else if (perfil.equalsIgnoreCase("Intermediário")) {
            minRisk = 1.01;
            maxRisk = 3.00;
        } else if (perfil.equalsIgnoreCase("Experiente")) {
            minRisk = 3.01;
        }

        final double finalMinRisk = minRisk;
        final double finalMaxRisk = maxRisk;

        return investimentos.stream()
                .filter(i -> i.risk() >= finalMinRisk && i.risk() <= finalMaxRisk)
                .toList();
    }
}