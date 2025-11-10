package com.google.genai.Financas.Service;


import org.apache.http.HttpException;

import java.io.IOException;

public interface IAService {
    String gerarResoista(String entrada) throws HttpException, IOException;
}


