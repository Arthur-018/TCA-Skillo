package com.google.genai.Financas.ModelExtrato;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosHorario(@JsonAlias("start_date")LocalDate dataInicial,
                           @JsonAlias("end_date")LocalDate dataFinal) {
}
