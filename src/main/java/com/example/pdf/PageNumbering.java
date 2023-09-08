package com.example.pdf;

/**
 * PageNumbering.
 *
 */
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PageNumbering extends PdfPageEventHelper {
    private int totalPages;

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        // При открытии документа устанавливаем общее количество страниц в 0
        totalPages = 0;
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        // При начале каждой страницы увеличиваем общее количество страниц на 1totalPages++;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        // Получаем текущую страницу
        int pageNumber = writer.getPageNumber();

        // Создаем объект Phrase для отображения номера страницы и общего количества страниц
        Phrase phrase = new Phrase("Страница " + pageNumber + " из " + totalPages);

        // Получаем размеры документа
        float pageWidth = document.getPageSize().getWidth();
        float pageHeight = document.getPageSize().getHeight();

        // Создаем объект PdfContentByte для отображения текста на каждой странице
        PdfContentByte canvas = writer.getDirectContent();
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase,
                pageWidth / 2, pageHeight - 20, 0);
    }

    public static void main(String[] args) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PageNumbering.pdf"));

            // Создаем объект PageNumbering и добавляем его в PdfWriter
            PageNumbering pageNumbering = new PageNumbering();
            writer.setPageEvent(pageNumbering);

            document.open();

            // Добавление содержимого на первую страницу
            Paragraph titleParagraph = new Paragraph("first page content");
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titleParagraph);

            // Создание разрыва страницы
            document.newPage();

            // Добавление содержимого на вторую страницу
            document.add(new Paragraph("second page content"));


            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}