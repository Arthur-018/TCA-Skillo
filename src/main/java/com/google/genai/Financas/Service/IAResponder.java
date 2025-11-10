package com.google.genai.Financas.Service;

import org.apache.http.HttpException;

import java.io.IOException;

public class IAResponder implements IAService {

    @Override
    public String gerarResposta(String entrada) throws HttpException, IOException {
        String prompt = ":\n" + entrada;
        return IAClient.chamarIA(prompt);
    }
}
