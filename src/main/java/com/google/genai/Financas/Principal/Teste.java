package com.google.genai.Financas.Principal;

import org.apache.http.HttpException;

import java.io.IOException;

public class Teste {
    public static void main(String[] args) throws HttpException, IOException {
PerfilFinanceiro p = new PerfilFinanceiro();
        System.out.println("perfil financeiro");

            p.pefilFinanceiro();

    }
}