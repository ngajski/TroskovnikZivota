package jaxrs.resource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.POIDocument;

import dao.DAOProvider;
import model.ExpenseCategory;
import model.ExpenseItem;
import model.ExpenseList;
import model.IncomeItem;
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
		return expenseList.getName();
	}
	
	@DELETE
	@Path("/remove/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String removeExpenseListFromDatabase(@PathParam("name_expenseList") String name){
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		DAOProvider.getDAO().removeExpenseListFromDatabase(expenseList);
		return "ok2";
	}
	
	
	
	@POST
	@Path("/income/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String addIncomeItem(@PathParam("name_expenseList") String name, IncomeItem item){
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		System.out.println(item);
		System.out.println(item.getAmounts().toString());
		expenseList.addNewIncomeItem(item);
		DAOProvider.getDAO().addIncomeItem(item);
		return "ok2";
	}
	
	
	@POST
	@Path("/category/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String addExpenseCategoryToExpenseList(@PathParam("name_expenseList") String name, ExpenseCategory expenseCategory) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		String catName = expenseCategory.getName().substring(0,expenseCategory.getName().indexOf('&'));
		String superCategoryName = expenseCategory.getName().substring(expenseCategory.getName().lastIndexOf("&")+1);
		expenseCategory.setName(catName);
		if (!superCategoryName.equals("Default")){
			ExpenseCategory superCategory = DAOProvider.getDAO().getCategoryByName(superCategoryName);
			expenseCategory.setSuperCategory(superCategory);
		}
		expenseList.addNewCategory(expenseCategory);
		DAOProvider.getDAO().addExpenseCategory(expenseCategory);
		return "ok1";
	}
	
	
	@POST
	@Path("/expenseitem/{category_name}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String addExpenseItemToExpenseList(@PathParam("category_name") String name, ExpenseItem expenseItem) {
		ExpenseCategory expenseCategory = DAOProvider.getDAO().getCategoryByName(name);
		expenseItem.setExpenseCategoryOwner(expenseCategory);
		expenseItem.setFixed(expenseCategory.isgetFixed());
		expenseCategory.getExpenseItems().add(expenseItem);
		DAOProvider.getDAO().addExpenseItem(expenseItem);
		return String.valueOf(expenseCategory.isgetFixed());
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
	@Path("/generate/{username}/{name}")
	@Produces("application/pdf")
	public Response generatePDF(@PathParam("username") String username, @PathParam("name") String name) throws IOException{
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
		
		File file = new File("troskovnik.pdf");
		document.save(file);
		document.close();
		
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=troskovnik_"+name+".pdf");
		response.header("Content-Type", "application/force-download");
		return response.build();
		
	}
			
	}