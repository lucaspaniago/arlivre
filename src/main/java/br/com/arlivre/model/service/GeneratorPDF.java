package br.com.arlivre.model.service;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratorPDF {
	
	public static void main(String[] args) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Lucas\\Desktop\\Lucas.pdf"));
            document.open();
            
            document.setPageSize(PageSize.A4); // modificado para o tamanho A4(já é o padrão);
            document.addSubject("Gerando PDF em Java"); document.addKeywords("avaliação física");
    		document.addCreator("Lucas");
    		document.addAuthor("Lucas Lopes Paniago");
            //document.getPageSize().setBackgroundColor(new BaseColor(192, 192, 192));
            // adicionando um parágrafo no documento document.add(new Paragraph("Gerando PDF - Java"));
    		document.add(new Paragraph("Gerando PDF - Java"));
    		
    		document.newPage();
    		document.add(new Paragraph("Novo parágrafo na nova página"));
    		document.add(new Paragraph("Outro novo parágrafo na nova página"));
    		
    		Image figura = Image.getInstance("C:\\Users\\Lucas\\gitavaliacao\\avaliacao\\src\\main\\webapp\\resources\\images\\pdf.png");
    		document.add(figura);

		}
		catch (DocumentException de){
			 System.err.println(de.getMessage());
		}
		catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		
		document.close();
	}
}
