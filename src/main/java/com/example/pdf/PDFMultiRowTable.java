package com.example.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PDFMultiRowTable {

    public static void main(String[] args) {
        
        try {
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("iTextTable2.pdf"));

            document.open();

            PdfPTable headerRow = new PdfPTable(3);
            headerRow.setKeepTogether(true);
            headerRow.addCell("Date");
            headerRow.addCell("Event");
            headerRow.addCell("Details");

//            PdfPTable firstRow = new PdfPTable(3);
//            firstRow.setKeepTogether(true);
//            firstRow.addCell("date 1");
//            firstRow.addCell("event 2 1");
//            firstRow.addCell("more\ndetails 2 1");

            PdfPTable secondRow = new PdfPTable(4);
            secondRow.setKeepTogether(true);
            PdfPCell cell = new PdfPCell(new Phrase("date 1"));
            cell.setRowspan(2);
            secondRow.addCell(cell);
            secondRow.addCell("event 1");
            secondRow.addCell("more\ndetails 2");
            secondRow.addCell("event 3");
            secondRow.addCell("details 4");
            secondRow.addCell("event 5");
            secondRow.addCell("details 6");
            cell = new PdfPCell(new Phrase("date 2"));
            cell.setRowspan(2);
            secondRow.addCell(cell);
            secondRow.addCell("event 11");
            secondRow.addCell("more\ndetails 12");
            secondRow.addCell("event 13");
            secondRow.addCell("details 14");
            secondRow.addCell("event 15");
            secondRow.addCell("details 16");

            document.add(headerRow);
            //document.add(firstRow);
            document.add(secondRow);

            document.newPage();

            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
