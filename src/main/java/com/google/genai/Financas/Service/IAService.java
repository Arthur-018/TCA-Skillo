package com.google.genai.Financas.Service;


import org.apache.http.HttpException;

import java.io.IOException;

public interface IAService {
    String gerarResposta(String entrada) throws HttpException, IOException;

    public interface ServicoIA {
        String gerarResposta(String entrada);


    }

}


