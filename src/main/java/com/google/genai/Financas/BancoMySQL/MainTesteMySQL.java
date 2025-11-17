package com.google.genai.Financas.BancoMySQL;

import java.util.Scanner;

public class MainTesteMySQL {
    public static void main(String[] args) {
        Declaracao declaracao = new Declaracao();
        Scanner leitura = new Scanner(System.in);

        int numero = 0;

        while (numero != 3) {
            System.out.println("""
                1 - Cadastrar novo usuario
                2 - Logar
                3 - sair
                """);

            int escolha = leitura.nextInt();
            leitura.nextLine();

            switch (escolha) {
                case 1 -> {
                    System.out.println("Digite seu nome");
                    String nome = leitura.nextLine();
                    System.out.println("Digite seu email");
                    String email = leitura.nextLine();
                    System.out.println("Digite sua senha");
                    String senhaUsuario = leitura.nextLine();

                    declaracao.CadastraUsuario(nome, email, senhaUsuario);
                }
                case 2 -> {
                    System.out.println("Vamos fazer login?");
                    System.out.println("Digite seu email");
                    String email2 = leitura.nextLine();
                    System.out.println("Digite sua senha");
                    String senhaUsuario2 = leitura.nextLine();

                    declaracao.LogarUsuario(email2, senhaUsuario2);
                }
                case 3 -> numero = 3;

                default -> System.out.println("Opção inválida!");
            }
        }
    }
}
