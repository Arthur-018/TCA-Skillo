package com.google.genai.Financas.ModelExtrato;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosBanco(@JsonAlias("bank_info")List<ObjetoBanco>list) {

}
