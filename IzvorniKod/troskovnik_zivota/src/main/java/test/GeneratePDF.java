package test;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GeneratePDF {
	public static void main(String[] args) throws IOException{
		generatePDF("fic123", "asdng");
	}
	
	public static void generatePDF( String username,String name) throws IOException{
//		List<ExpenseList> expenseLists = getExpenseListsForUsername(username);
//		ExpenseList expenseList = new ExpenseList();
//		for(ExpenseList list : expenseLists) {
//			if(list.getName().equals(name)) {
//				expenseList = list;
//				break;
//			}
//		}
		
		PDDocument document = new PDDocument();
		PDDocumentInformation info = new PDDocumentInformation();
		info.setAuthor(username);
		info.setTitle(name);
		info.setSubject("Troskovnik zivota: " + name);
		document.setDocumentInformation(info);
		
		PDPageContentStream stream;
		PDPage page;
		//String text;
		
		page = new PDPage();
		stream = new PDPageContentStream(document,page);
		stream.beginText();
		stream.setFont(PDType1Font.COURIER_BOLD, 12);
		stream.setLeading(14.5f);
		stream.newLineAtOffset(250, 700);
		stream.showText("TROŠKOVNIK ŽIVOTA");
		stream.newLine();
		stream.showText("IME: " + name);
		stream.newLine();
		stream.showText("KORISNIK: " + username);
		stream.endText();
		stream.close();
		document.addPage(page);
		
		
		File tr = new File("C://Users/Vilim/Desktop/troskovnik.pdf");
		document.save(tr);
		document.close();
		//return "ok";
//		File file = new File("troskovnik.pdf");
//		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
//			      .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
//			      .build();
		
	}
}
