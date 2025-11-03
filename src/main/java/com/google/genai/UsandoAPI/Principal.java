package com.google.genai.UsandoAPI;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.apache.http.HttpException;

import java.io.IOException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws HttpException, IOException {
        Scanner scan = new Scanner(System.in);
        String caminhoPDF = "C:/Users/Skillo-Danilo/Desktop/Document.pdf";
        String textoPDF = LeitorPDF.lerTextoDoPDF(caminhoPDF);


        while (true) {
            String escolha = scan.nextLine();
            Client client = Client.builder().apiKey("AIzaSyDQwNQ3rC7lIylLeX4ir9ywErfnd_Q_UJk").build();

            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",
                            escolha + textoPDF,
                            null);

            System.out.println(response.text());
        }
    }
}
