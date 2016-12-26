package test;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GeneratePDF {
	public static void main(String[] args) throws IOException{
		PDDocument document = new PDDocument();
		PDPageContentStream stream;
		
		
		for (int i=0; i<5; i++){
			PDPage page = new PDPage();
			stream = new PDPageContentStream(document,page);
			
			stream.beginText();
			stream.setFont(PDType1Font.COURIER_BOLD, 12);
			stream.newLineAtOffset(225, 760);
			String text = "Troškovnik mamu ti jebem";
			stream.showText(text);
			stream.endText();
			
			
			
			stream.close();
			document.addPage(page);
		}
		
		
		document.save("C:\\Users\\vrlji\\Desktop\\mypdf.pdf");
		document.close();
	}
}
