package com.google.genai.Financas.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.apache.http.HttpException;
import java.io.IOException;

public class IAUtil {
    private static final String API_KEY = "AIzaSyDQwNQ3rC7lIylLeX4ir9ywErfnd_Q_UJk";
    private static final Client client = Client.builder().apiKey(API_KEY).build();
    public static String gerarResposta(String prompt) throws HttpException, IOException {
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null
                );
        String resposta = response.text();
        String historico = "Prompt:\n" + prompt + "\n\nResposta:\n" + resposta + "\n\n---\n";
        HistoricoPDF.salvarPrompt(historico, "caminhoPrompts");
        return resposta;
    }
}
