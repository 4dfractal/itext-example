package com.example.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFNewPage {

    protected static final Font TITLE_FONT = createTitleFont();
    private static final String ARIAL_FONT_PATH = "fonts/Arial.ttf";

    public static void main(String[] args) {
        // Создание нового документа
        Document document = new Document();

        try {
            // Создание объекта PdfWriter для записи в документ
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("iTextNewPage.pdf"));

            // Открытие документа
            document.open();

//            PdfPTable headerRow = new PdfPTable(3);
//            headerRow.setKeepTogether(true);
//            headerRow.addCell("Date");
//            headerRow.addCell("Event");
//            headerRow.addCell("Details");
//            document.add(headerRow);


            // Добавление содержимого на первую страницу
            Paragraph titleParagraph = new Paragraph("wewqewqewqewqeСодержимое первой страницы", TITLE_FONT);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titleParagraph);

            // Создание разрыва страницы
            document.newPage();


//            PdfPTable headerRow2 = new PdfPTable(3);
//            headerRow2.setKeepTogether(true);
//            headerRow2.addCell("Date");
//            headerRow2.addCell("Event");
//            headerRow2.addCell("Details");
//            document.add(headerRow2);

            // Добавление содержимого на вторую страницу
            document.add(new Paragraph("wqewqeqwewqСодержимое второй страницы", TITLE_FONT));

            // Закрытие документа
            document.close();

            System.out.println("PDF создан успешно!");

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Font createTitleFont() {
        Font font = createReportFont();
        font.setSize(16);
        font.setStyle(Font.BOLD);
        return font;
    }

    private static Font createReportFont() {
        Font font;
        try {
            BaseFont baseFont = BaseFont.createFont(ARIAL_FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            font = new Font(baseFont);
        } catch (DocumentException | IOException e) {
            font = FontFactory.getFont(FontFactory.HELVETICA);
        }
        font.setSize(10);
        return font;
    }

}
