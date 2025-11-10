package com.google.genai.Financas.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class HistoricoPDF {
    public static void salvarPrompt(String texto,  String caminhoArquivo) throws IOException{
        PDDocument documento;
        File arquivo = new File(caminhoArquivo);
        if(arquivo.exists()){
            documento = PDDocument.load(arquivo);
        }
        else {
            documento = new PDDocument();
        }
        PDPage pagina = new PDPage(PDRectangle.A4);
        documento.addPage(pagina);
        PDPageContentStream conteudo = new PDPageContentStream(documento, pagina);
        conteudo.beginText();
        conteudo.setFont(PDType1Font.HELVETICA,12);
        conteudo.setLeading(14.5f);
        conteudo.newLineAtOffset(50,750);

        String[] linhas = texto.split("\n");
        for (String linha : linhas){
            conteudo.showText(linha);
            conteudo.newLine();
        }
        conteudo.endText();
        conteudo.close();

        documento.save(caminhoArquivo);
        documento.close();
    }

}
