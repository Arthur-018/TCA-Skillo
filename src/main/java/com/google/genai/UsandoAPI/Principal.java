package com.google.genai.UsandoAPI;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GenerationConfig;
import org.apache.http.HttpException;
import com.google.genai.UsandoAPI.HistoricoPDF;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws HttpException, IOException {
        Scanner scan = new Scanner(System.in);
        String escolha = scan.nextLine();

        String caminhoExtrato = "C:/Users/Skillo-Danilo/Desktop/Extrato-01-10-2025-a-30-10-2025-PDF.pdf";
        String textoExtrato = LeitorPDF.lerTextoDoPDF(caminhoExtrato);

       String caminhoPrompts = "C:/Users/Skillo-Danilo/Desktop/historico_prompts.pdf";
       String textoPrompts = LeitorPDF.lerTextoDoPDF(caminhoPrompts);






        while (true) {

            escolha = scan.nextLine();
            Client client = Client.builder().apiKey("AIzaSyDQwNQ3rC7lIylLeX4ir9ywErfnd_Q_UJk").build();

            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",
                               textoPrompts +escolha + textoExtrato,
                            null);

            System.out.println(response.text());
            String historico = "Prompt:\n" + escolha + "\n\nResposta:\n" + response.text() + "\n\n------------------------\n";
            HistoricoPDF.salvarPrompt(historico, caminhoPrompts);
        }
    }
}
