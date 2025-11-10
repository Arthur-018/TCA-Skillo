package com.google.genai.Financas.Service;

import com.google.genai.Financas.Service.HistoricoPDF;
import com.google.genai.Financas.Service.IAClient;

import org.apache.http.HttpException;

import java.io.IOException;

public class IAResponder {
    public String gerarResposta(String entrada, String historicoLocal) throws HttpException, IOException {
        String prompt = ":\n" + entrada;
        String resposta = IAClient.chamarIA(prompt);

        String historico = "Prompt:\n" + prompt + "\n\nResposta:\n" + resposta + "\n\n---\n";
        HistoricoPDF.salvarPrompt(historico, historicoLocal);

        return resposta;
    }
}
