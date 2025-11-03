package com.google.genai.UsandoAPI;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class LeitorPDF {
    public static String lerTextoDoPDF(String caminhoArquivo) throws IOException {
        try (PDDocument documento = PDDocument.load(new File(caminhoArquivo))){
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(documento);
        }
    }
}
