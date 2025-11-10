package com.google.genai.Financas.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.apache.http.HttpException;

import java.io.IOException;

public class IAClient {

    private static final String API_KEY = "AIzaSyDQwNQ3rC7lIylLeX4ir9ywErfnd_Q_UJk";

    public static String chamarIA(String prompt) throws HttpException, IOException {
        Client client = Client.builder().apiKey(API_KEY).build();

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        );

        return response.text();
    }
}
