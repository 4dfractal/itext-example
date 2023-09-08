package com.example.pdf;

/**
 * PageNumbering.
 */

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class PageNumbering2 extends PdfPageEventHelper {
    // This is the contentbyte object of the writer
    PdfContentByte cb;

    // we will put the final number of pages in a template
    PdfTemplate headerTemplate, footerTemplate;

    // this is the BaseFont we are going to use for the header / footer
    BaseFont bf = null;

    // This keeps track of the creation time
    LocalDateTime PrintTime = LocalDateTime.now();

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            PrintTime = LocalDateTime.now();
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb = writer.getDirectContent();
            headerTemplate = cb.createTemplate(100, 100);
            footerTemplate = cb.createTemplate(50, 50);
        } catch (DocumentException de) {
            //handle exception here
        } catch (IOException ioe) {
            //handle exception here
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        {
            super.onEndPage(writer, document);

            Font baseFontNormal = new Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL, BaseColor.BLACK);

            Font baseFontBig = new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD, BaseColor.BLACK);

            Phrase p1Header = new Phrase("Sample Header Here", baseFontNormal);

            //Create PdfTable object
            PdfPTable pdfTab = new PdfPTable(3);

            //We will have to create separate cells to include image logo and 2 separate strings
            //Row 1
            PdfPCell pdfCell1 = new PdfPCell();
            PdfPCell pdfCell2 = new PdfPCell(p1Header);
            PdfPCell pdfCell3 = new PdfPCell();
            String text = "Page " + writer.getPageNumber() + " of ";


            //Add paging to header
            {
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.setTextMatrix(document.getPageSize().getRight(200), document.getPageSize().getTop(45));
                cb.showText(text);
                cb.endText();
                float len = bf.getWidthPoint(text, 12);
                //Adds "12" in Page 1 of 12
                cb.addTemplate(headerTemplate, document.getPageSize().getRight(200) + len, document.getPageSize().getTop(45));
            }
            //Add paging to footer
            {
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.setTextMatrix(document.getPageSize().getRight(180), document.getPageSize().getBottom(30));
                cb.showText(text);
                cb.endText();
                float len = bf.getWidthPoint(text, 12);
                cb.addTemplate(footerTemplate, document.getPageSize().getRight(180) + len, document.getPageSize().getBottom(30));
            }
            //Row 2
            PdfPCell pdfCell4 = new PdfPCell(new Phrase("Sub Header Description", baseFontNormal));
            //Row 3


            PdfPCell pdfCell5 = new PdfPCell(new Phrase("Date:" + PrintTime, baseFontBig));
            PdfPCell pdfCell6 = new PdfPCell();
            PdfPCell pdfCell7 = new PdfPCell(new Phrase("TIME:" + String.format("{0:t}", LocalDateTime.now()), baseFontBig));


            //set the alignment of all three cells and set border to 0
            pdfCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell7.setHorizontalAlignment(Element.ALIGN_CENTER);


            pdfCell2.setVerticalAlignment(Element.ALIGN_BOTTOM);
            pdfCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfCell4.setVerticalAlignment(Element.ALIGN_TOP);
            pdfCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfCell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfCell7.setVerticalAlignment(Element.ALIGN_MIDDLE);


            pdfCell4.setColspan(3);


            pdfCell1.setBorder(0);
            pdfCell2.setBorder(0);
            pdfCell3.setBorder(0);
            pdfCell4.setBorder(0);
            pdfCell5.setBorder(0);
            pdfCell6.setBorder(0);
            pdfCell7.setBorder(0);


            //add all three cells into PdfTable
            pdfTab.addCell(pdfCell1);
            pdfTab.addCell(pdfCell2);
            pdfTab.addCell(pdfCell3);
            pdfTab.addCell(pdfCell4);
            pdfTab.addCell(pdfCell5);
            pdfTab.addCell(pdfCell6);
            pdfTab.addCell(pdfCell7);

            pdfTab.setTotalWidth(document.getPageSize().getWidth() - 80f);
            pdfTab.setWidthPercentage(70);


            //call WriteSelectedRows of PdfTable. This writes rows from PdfWriter in PdfTable
            //first param is start row. -1 indicates there is no end row and all the rows to be included to write
            //Third and fourth param is x and y position to start writing
            pdfTab.writeSelectedRows(0, -1, 40, document.getPageSize().getHeight() - 30, writer.getDirectContent());

            //Move the pointer and draw line to separate header section from rest of page
            cb.moveTo(40, document.getPageSize().getHeight() - 100);
            cb.lineTo(document.getPageSize().getWidth() - 40, document.getPageSize().getHeight() - 100);
            cb.stroke();

            //Move the pointer and draw line to separate footer section from rest of page
            cb.moveTo(40, document.getPageSize().getBottom(50));
            cb.lineTo(document.getPageSize().getWidth() - 40, document.getPageSize().getBottom(50));
            cb.stroke();
        }

    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        super.onCloseDocument(writer, document);

        headerTemplate.beginText();
        headerTemplate.setFontAndSize(bf, 12);
        headerTemplate.setTextMatrix(0, 0);
        headerTemplate.showText(String.valueOf(writer.getPageNumber() - 1));
        headerTemplate.endText();

        footerTemplate.beginText();
        footerTemplate.setFontAndSize(bf, 12);
        footerTemplate.setTextMatrix(0, 0);
        footerTemplate.showText(String.valueOf(writer.getPageNumber() - 1));
        footerTemplate.endText();


    }

    public static void main(String[] args) {
        Document document = new Document(PageSize.A4, 10f, 10f, 140f, 10f);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PageNumbering2.pdf"));

            // Создаем объект PageNumbering и добавляем его в PdfWriter
            PageNumbering2 pageNumbering2 = new PageNumbering2();
            writer.setPageEvent(pageNumbering2);

            document.open();

//            // Добавление содержимого на первую страницу
//            Paragraph titleParagraph = new Paragraph("first page content");
//            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
//            document.add(titleParagraph);
//
//            // Создание разрыва страницы
//            document.newPage();
//
//            // Добавление содержимого на вторую страницу
//            document.add(new Paragraph("second page content"));


            for (int i = 0; i < 5; i++)
            {
                Paragraph para = new Paragraph("Hello world. Checking Header Footer", new Font(Font.FontFamily.HELVETICA, 22));
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                document.newPage();
            }


            document.close();

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}