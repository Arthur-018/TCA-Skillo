package com.google.genai.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ObjetoBanco(@JsonAlias("instituition") String instituicaoBanco) {
}

