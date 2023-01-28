package com.Reservations.Dodaci;

import java.awt.Color;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.Reservations.DTO.PrihodDTO;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PrihodPDFGenerator {
	private List<PrihodDTO> lista;
	private Date pocDatum;
	private Date krajDatum;
	
    public PrihodPDFGenerator(List<PrihodDTO> lista, Date pocDatum, Date krajDatum) {
        this.lista = lista;
        this.pocDatum = pocDatum;
        this.krajDatum = krajDatum;
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Vrednost", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Rezervacija ID", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Datum", font));
        table.addCell(cell);
    }	
     
    private void writeTableData(PdfPTable table) {
        for (PrihodDTO p : lista) {
        	 Calendar c = Calendar.getInstance();
             c.setTime(pocDatum); 
        	while(!c.getTime().after(krajDatum))
        	{
//                table.addCell(String.valueOf(p.getVrednost()));
//                table.addCell(String.valueOf(p.getRezervacija().getID()));
//                table.addCell(p.getRezervacija().getDatum().toString());
        	}
        }
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
}
