package jaxrs.resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import dao.DAOProvider;
import model.ExpenseList;
import wrappers.StringWrapper;
import model.User;
import model.time.Period;

@Path("/expenseList")
public class ExpenseListResource {

	@GET
	@Path("/ping")
	public String getServerTime() {
		System.out.println("RESTful Service 'ExpenseListResource' is running ==> ping");
		return "received ping on " + new Date().toString();
	}

	@GET
	@Path("/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ExpenseList> getExpenseListsForUsername(@PathParam("username") String username){
		return DAOProvider.getDAO().getExpenseListsForUsername(username);
	}

	@POST
	@Path("/{username}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String addExpenseListToUser(@PathParam("username") String username, ExpenseList expenseList) {
		User user = DAOProvider.getDAO().getUserByUsername(username);
		expenseList.setUserOwner(user);
		DAOProvider.getDAO().addExpenseList(expenseList);
		return "ok";
	}
	
	@GET
	@Path("/defaultCategories")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<StringWrapper> getDefaultExpenseCategories() {
		List<StringWrapper> defaultCategories = new LinkedList<>();
		defaultCategories.add(new StringWrapper("Hrana"));
		defaultCategories.add(new StringWrapper("Nekretnine"));// , "Hrana",
																// "Pokretnine",
																// "Kozmetika"));
		defaultCategories.add(new StringWrapper("Pokretnine"));
		defaultCategories.add(new StringWrapper("Kozmetika"));
		return defaultCategories;
	}

	@GET
	@Path("/defaultPeriods")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<StringWrapper> getDefaultPeriods() {
		List<StringWrapper> defaultPeriods = new LinkedList<>();
		Period[] periods = Period.values();
		for (int i = 0; i < periods.length; ++i) {
			defaultPeriods.add(new StringWrapper(periods[i].toString()));
		}
		return defaultPeriods;
	}
	
	@GET
	@Path("/generate/{name}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String generatePDF(@PathParam("name") String name) throws IOException{
		PDDocument document = new PDDocument();
		PDPageContentStream stream;
		
		
		for (int i=0; i<5; i++){
			PDPage page = new PDPage();
			stream = new PDPageContentStream(document,page);
			
			stream.beginText();
			stream.setFont(PDType1Font.COURIER_BOLD, 12);
			stream.newLineAtOffset(225, 760);
			String text = name;
			stream.showText(text);
			stream.endText();
			
			
			
			stream.close();
			document.addPage(page);
		}
		
//		MENJAJ USERA
		document.save("C:\\Users\\vrlji\\Desktop\\mypdf.pdf");
		document.close();
		return "ok";
	}
			
	}