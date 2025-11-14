package com.google.genai.Financas.TestandoAPIInvestimentos;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class ApiClient {

    protected WebClient client;

    public ApiClient(String baseUrl) {
        this.client = WebClient.builder().baseUrl(baseUrl).build();
    }

    protected String get(String endpoint) {
        try {
            return client.get()
                    .uri(endpoint)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.out.println("Erro ao acessar a API: " + e.getMessage());
            return "{}";
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            return "{}";
        }
    }
}