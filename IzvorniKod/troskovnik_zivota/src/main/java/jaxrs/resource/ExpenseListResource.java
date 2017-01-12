package jaxrs.resource;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.pdfbox.pdmodel.PDDocument;

import dao.DAOProvider;
import model.ExpenseCategory;
import model.ExpenseItem;
import model.ExpenseList;
import model.IncomeItem;
import wrappers.StringWrapper;
import model.User;
import model.time.Period;
import pdf.PDFGenerator;

@Path("/expenseList")
public class ExpenseListResource {

	final String usernameLoggin = "oppgroupteflja@gmail.com";
	final String password = "Qwertzuiop1";

	@GET
	@Path("/ping")
	public String getServerTime() {
		System.out.println("RESTful Service 'ExpenseListResource' is running ==> ping");
		return "received ping on " + new Date().toString();
	}

	@GET
	@Path("/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ExpenseList> getExpenseListsForUsername(@PathParam("username") String username) {
		return DAOProvider.getDAO().getExpenseListsForUsername(username);
	}

	@GET
	@Path("/byName/{expenseListName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ExpenseList getExpenseList(@PathParam("expenseListName") String name) {
		return DAOProvider.getDAO().getExpenseListByName(name);
	}

	@GET
	@Path("/parentlessCategories/{expenseListName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> getParentlessCategories(@PathParam("expenseListName") String name) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		List<String> parentlessCategories = new ArrayList<String>();
		for (ExpenseCategory category : expenseList.getExpenseCategories()) {
			if (category.getSuperCategory() == null) {
				parentlessCategories.add(category.getId().toString());
				// System.out.println(category.getId().toString());
			}
		}
		return parentlessCategories;
	}

	@POST
	@Path("/{username}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String addExpenseListToUser(@PathParam("username") String username, ExpenseList expenseList) {
		ExpenseList expenseListCheck = DAOProvider.getDAO().getExpenseListByName(expenseList.getName());
		if (expenseListCheck == null) {
			User user = DAOProvider.getDAO().getUserByUsername(username);
			expenseList.setUserOwner(user);
			DAOProvider.getDAO().addExpenseList(expenseList);
			return "DA";
		} else {
			return "NE";
		}
	}

	@DELETE
	@Path("/remove/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String removeExpenseList(@PathParam("name_expenseList") String name) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		DAOProvider.getDAO().removeExpenseListFromDatabase(expenseList);
		return "DA";
	}

	@DELETE
	@Path("/removeIncomeItem/{id}/{name_expenseList}")
	@Produces({MediaType.TEXT_PLAIN})
	public String removeIncomeItem(@PathParam("name_expenseList") String name, @PathParam("id") Long id){
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		for (IncomeItem item : expenseList.getIncomeItems()){
			if (item.getId() == id ){
				DAOProvider.getDAO().removeIncomeItem(item);
				return "DA";
			}
			
		}
		
		return "DA";
		
		
	}
	
	
	@DELETE
	@Path("/removeExpenseItem/{id}/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String removeExpenseItem(@PathParam("name_expenseList") String name, @PathParam("id") Long id) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		for ( ExpenseCategory category : expenseList.getExpenseCategories()){
			for ( ExpenseItem item : category.getExpenseItems()){
				if ( item.getId() == id ){
					System.out.println("\n\ndoso u if remove expense item\n\n");
					DAOProvider.getDAO().removeExpenseItem(item);
					return "DA";
				}
			}
		}
		return "DA";
	}
	
	
	@DELETE
	@Path("/removeCategory/{currentCategoryName}/{expenseListName}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String removeExpenseCategoryFromDatabase(@PathParam("currentCategoryName") String name,
			@PathParam("expenseListName") String expenseListName) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(expenseListName);
		for ( ExpenseCategory category : expenseList.getExpenseCategories()){
			if ( category.getName().equals(name)){
				DAOProvider.getDAO().removeExpenseCategory(category);
			}
		}
		return "DA";
	}

	@POST
	@Path("/expenseCategory")
	@Produces({ MediaType.TEXT_PLAIN })
	public String editExpenseCategory(ExpenseCategory expenseCategory) {
		ExpenseCategory category = DAOProvider.getDAO().getCategoryByID(expenseCategory.getId());
		for (ExpenseCategory cat : category.getExpenseListOwner().getExpenseCategories()){
			if ( cat.getName().equals(expenseCategory.getName()) && (cat.getId() != expenseCategory.getId())){
				return "NE";
			}
		}
		category.setName(expenseCategory.getName());
		category.issetFixed(expenseCategory.isgetFixed());
		return "SVE PET";
	}

	@POST
	@Path("/expenseItem")
	@Produces({ MediaType.TEXT_PLAIN })
	public String editExpenseItem(ExpenseItem expenseItem) {

		ExpenseItem item = DAOProvider.getDAO().getExpenseItemByID(expenseItem.getId());
		for ( ExpenseCategory cat : item.getExpenseCategoryOwner().getExpenseListOwner().getExpenseCategories()){
			for ( ExpenseItem itm : cat.getExpenseItems()){
				if ( itm.getName().equals(expenseItem.getName()) && (itm.getId() != expenseItem.getId())){
					return "NE";
				}
			}
		}
		item.setComment(expenseItem.getComment());
		item.setName(expenseItem.getName());
		item.setEndDate(expenseItem.getEndDate());
		item.setStartDate(expenseItem.getStartDate());
		item.setPeriod(expenseItem.getPeriod());
		item.issetFixed(expenseItem.isFixed());
		item.setAmounts(expenseItem.getAmounts());
		return "SVE PET";
	}

	@POST
	@Path("/incomeItem")
	@Produces({ MediaType.TEXT_PLAIN })
	public String editIncomeItem(IncomeItem incomeItem) {
		IncomeItem item = DAOProvider.getDAO().getIncomeItemByID(incomeItem.getId());
		for ( IncomeItem itm : item.getExpenseListOwner().getIncomeItems()){
			if ( itm.getName().equals(incomeItem.getName()) && (itm.getId() != incomeItem.getId())){
				return "NE";
			}
		}
		item.setComment(incomeItem.getComment());
		item.setName(incomeItem.getName());
		item.setEndDate(incomeItem.getEndDate());
		item.setStartDate(incomeItem.getStartDate());
		item.setPeriod(incomeItem.getPeriod());
		item.issetFixed(incomeItem.isFixed());
		item.setAmounts(incomeItem.getAmounts());
		return "SVE PET";
	}

	@POST
	@Path("/expenseListRename/{oldName}/{newName}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String renameExpenseList(@PathParam("oldName") String oldName, @PathParam("newName") String newName) {
		ExpenseList expenseListOld = DAOProvider.getDAO().getExpenseListByName(oldName);
		ExpenseList expenseListNew = DAOProvider.getDAO().getExpenseListByName(newName);
		if (expenseListNew == null) {
			expenseListOld.setName(newName);
			return "DA";
		} else {
			return "NE";
		}
	}

	@POST
	@Path("/income/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String addIncomeItem(@PathParam("name_expenseList") String name, IncomeItem item) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		for (IncomeItem itemCheck : expenseList.getIncomeItems()) {
			if (itemCheck.getName().equals(item.getName())) {
				return "NE";
			}
		}
		expenseList.addNewIncomeItem(item);
		DAOProvider.getDAO().addIncomeItem(item);
		return "DA";
	}

	@POST
	@Path("/category/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String addExpenseCategoryToExpenseList(@PathParam("name_expenseList") String name,
			ExpenseCategory expenseCategory) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		for (ExpenseCategory categoryCheck : expenseList.getExpenseCategories()) {
			if (categoryCheck.getName()
					.equals(expenseCategory.getName().substring(0, expenseCategory.getName().indexOf('&')))) {
				return "NE";
			}
		}
		String catName = expenseCategory.getName().substring(0, expenseCategory.getName().indexOf('&'));
		String superCategoryName = expenseCategory.getName().substring(expenseCategory.getName().lastIndexOf("&") + 1);
		expenseCategory.setName(catName);
		if (!superCategoryName.equals("Default")) {
			for (ExpenseCategory category : expenseList.getExpenseCategories()) {
				if (category.getName().equals(superCategoryName)) {
					ExpenseCategory superCategory = DAOProvider.getDAO().getCategoryByName(superCategoryName);
					expenseCategory.setSuperCategory(superCategory);
					break;
				}
			}
		}
		expenseCategory.setExpenseListOwner(expenseList);
		expenseList.addNewCategory(expenseCategory);

		DAOProvider.getDAO().addExpenseCategory(expenseCategory);
		return "DA";
	}

	@POST
	@Path("/expenseitem/{category_name}/{name}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String addExpenseItem(@PathParam("category_name") String categoryName,
			@PathParam("name") String name, ExpenseItem expenseItem) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		for (ExpenseCategory category : expenseList.getExpenseCategories()) {
			for (ExpenseItem item : category.getExpenseItems()) {
				if (item.getName().equals(expenseItem.getName())) {
					return "NE";
				}
			}
		}
		
		System.out.println(expenseItem.getAmounts().toString());
		
		ExpenseCategory expenseCategory = null;
		for (ExpenseCategory category : expenseList.getExpenseCategories()) {
			if (category.getName().equals(categoryName)) {
				expenseCategory = category;
			}
		}
		
//		expenseItem.validateDates();
//		expenseItem.validateAmounts();
		expenseItem.setExpenseCategoryOwner(expenseCategory);
		expenseItem.issetFixed(expenseCategory.isgetFixed());
		expenseCategory.getExpenseItems().add(expenseItem);
		DAOProvider.getDAO().addExpenseItem(expenseItem);
		return "DA";
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
	@Path("/expenselistTree/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ExpenseList getExpenseListByName(@PathParam("name") String name) {
		return DAOProvider.getDAO().getExpenseListByName(name);
	}

	@GET
	@Path("/expensecategory/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ExpenseCategory getExpenseCategoryByID(@PathParam("id") Long id) {
		return DAOProvider.getDAO().getCategoryByID(id);
	}

	@GET
	@Path("/expensecategoryByExpenseList/{category_name}/{expenseListName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ExpenseCategory getExpenseCategoryByExpenseList(@PathParam("category_name") String categoryName,
			@PathParam("expenseListName") String name) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		for (ExpenseCategory category : expenseList.getExpenseCategories()) {
			if (category.getName().equals(categoryName)) {
				return category;
			}
		}
		return null;
	}

	@GET
	@Path("/getexpenseitem/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ExpenseItem getExpenseItemByID(@PathParam("name") String id) {
		return DAOProvider.getDAO().getExpenseItemByID(Long.parseLong(id));
	}

	@GET
	@Path("/getincomeitem/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public IncomeItem getIncomeItemByID(@PathParam("name") String id) {
		return DAOProvider.getDAO().getIncomeItemByID(Long.parseLong(id));
	}

	@GET
	@Path("/check/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Boolean checkExpenseListForUsername(@PathParam("username") String username) {
		User user = DAOProvider.getDAO().getUserByUsername(username);
		List<ExpenseList> expenseLists = user.getExpenseLists();

		if (expenseLists != null && expenseLists.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@POST
	@Path("/store/{username}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String addExpenseListsToUser(@PathParam("username") String username, List<ExpenseList> expenseLists) {
		User user = DAOProvider.getDAO().getUserByUsername(username);

		for (ExpenseList expenseList : expenseLists) {
			expenseList.revalidate(user);
			DAOProvider.getDAO().addExpenseList(expenseList);
		}

		return "Uspiješno dodao troškovnike.";
	}

	@GET
	@Path("/generate/{username}/{name}")
	@Produces("application/pdf")
	public Response generatePDF(@PathParam("username") String username, @PathParam("name") String name)
			throws IOException {
		List<ExpenseList> expenseLists = getExpenseListsForUsername(username);
		ExpenseList expenseList = new ExpenseList();
		for (ExpenseList list : expenseLists) {
			if (list.getName().equals(name)) {
				expenseList = list;
				break;
			}
		}

		File pdfFile = cretePDFFile(username, expenseList);

		System.out.println("doso sam do tu");
		ResponseBuilder response = Response.ok((Object) pdfFile);
		response.header("Content-Disposition", "attachment; filename=troskovnik_" + expenseList.getName() + ".pdf");
		response.header("Content-Type", "application/force-download");
		return response.build();
	}

	@POST
	@Path("/sendEmail/{username}/{expenseListName}/{email}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Boolean sendEmail(@PathParam("username") String usernameFrom,
			@PathParam("expenseListName") String expenseListName, @PathParam("email") String emailTo)
			throws IOException {
		List<ExpenseList> expenseLists = getExpenseListsForUsername(usernameFrom);
		ExpenseList expenseList = new ExpenseList();
		for (ExpenseList list : expenseLists) {
			if (list.getName().equals(expenseListName)) {
				expenseList = list;
				break;
			}
		}

		File pdfFile = cretePDFFile(usernameFrom, expenseList);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(usernameLoggin, password);
			}
		});

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(usernameLoggin, usernameFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setSubject("Ime troškovnika: " + expenseListName);

			String text = "Troškovnik je poslan u " + new Date().toString() + ".";

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(text);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String fileName = "troskovnik_" + expenseList.getName() + ".pdf";
			DataSource source = new FileDataSource(pdfFile);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		return true;

	}

	private File cretePDFFile(String username, ExpenseList expenseList) throws IOException {
		PDDocument document = new PDDocument();
		PDFGenerator.generatePDF(document, username, expenseList);
		File file = new File("troskovnik.pdf");
		document.save(file);
		document.close();

		return file;
	}

}
