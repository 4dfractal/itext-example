package com.example.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RotatedCell {

    public static final String DEST = "rotated_cell.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        //File file = new File(DEST);
        //file.getParentFile().mkdirs();
        new RotatedCell().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(32);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        for (int i = 0; i < 32; i++) {
            PdfPCell cell =
                    new PdfPCell(new Phrase(String.format("Long name of the exit %s\n number of bags ", i)));
            cell.setRotation(90);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }
        for(int i = 0; i < 64; i++){
            table.addCell("hi");
        }
        document.add(table);
        document.close();
    }


}
