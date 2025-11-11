package com.google.genai.Financas.Principal;

import java.io.IOException;

public class Teste {
    public static void main(String[] args) throws IOException {
        System.out.println("==========================================");
        System.out.println("=== 💹 SIMULADOR DE PERFIL FINANCEIRO ===");
        System.out.println("==========================================\n");

        PerfilFinanceiro perfil = new PerfilFinanceiro();
        perfil.pefilFinanceiro();

        System.out.println("\n✅ Análise concluída!");
        System.out.println("Obrigado por utilizar o simulador 💰");
    }
}