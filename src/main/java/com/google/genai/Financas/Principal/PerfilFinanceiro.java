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
        System.out.println("(c) Compraria mais");
        resposta = scan.nextLine();
        if (resposta.equalsIgnoreCase("a")) pontos += 1;
        else if (resposta.equalsIgnoreCase("b")) pontos += 2;
        else if (resposta.equalsIgnoreCase("c")) pontos += 3;

        System.out.println("\n3️⃣ Qual seu objetivo?");
        System.out.println("(a) Segurança");
        System.out.println("(b) Crescimento moderado");
        System.out.println("(c) Crescimento agressivo");
        resposta = scan.nextLine();
        if (resposta.equalsIgnoreCase("a")) pontos += 1;
        else if (resposta.equalsIgnoreCase("b")) pontos += 2;
        else if (resposta.equalsIgnoreCase("c")) pontos += 3;

        System.out.println("\n4️⃣ Por quanto tempo vai deixar o dinheiro investido?");
        System.out.println("(a) Menos de 1 ano");
        System.out.println("(b) 1 a 5 anos");
        System.out.println("(c) Mais de 5 anos");
        resposta = scan.nextLine();
        if (resposta.equalsIgnoreCase("a")) pontos += 1;
        else if (resposta.equalsIgnoreCase("b")) pontos += 2;
        else if (resposta.equalsIgnoreCase("c")) pontos += 3;

        System.out.println("\n5️⃣ Já investiu com risco de perda?");
        System.out.println("(a) Nunca");
        System.out.println("(b) Sim, pouco dinheiro");
        System.out.println("(c) Sim, boa parte da carteira");
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

        System.out.println("\n🇧🇷 AÇÕES NACIONAIS:");
        for (Investment i : brapi.getInvestments()) System.out.println(i);

        System.out.println("\n🌍 AÇÕES INTERNACIONAIS:");
        for (Investment i : twelve.getStocks()) System.out.println(i);

        System.out.println("\n💎 CRIPTOMOEDAS:");
        for (Investment i : twelve.getCryptos()) System.out.println(i);
    }
}