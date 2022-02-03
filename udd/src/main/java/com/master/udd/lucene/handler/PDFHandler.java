package com.master.udd.lucene.handler;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

@Component
public class PDFHandler {

    public String getText(File file) {
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            PDFTextStripper textStripper = new PDFTextStripper();
            PDDocument doc = parser.getPDDocument();
            String text = textStripper.getText(doc);
            doc.close();
            return text;
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }
        return null;
    }

}
