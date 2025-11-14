package com.google.genai.Financas.TestandoAPIInvestimentos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    protected final HttpClient client;
    protected final String baseUrl;

    public ApiClient(String baseUrl) {
        this.client = HttpClient.newHttpClient();
        this.baseUrl = baseUrl;
    }

    protected String get(String endpoint) {
        URI uri = URI.create(baseUrl + endpoint);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("Erro na requisição. Status Code: " + response.statusCode() + " para URI: " + uri);
                return "{}";
            }
        } catch (IOException e) {
            System.out.println("Erro de I/O (conexão/rede) ao fazer a requisição: " + e.getMessage());
            return "{}";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Requisição interrompida.");
            return "{}";
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            return "{}";
        }
    }
}