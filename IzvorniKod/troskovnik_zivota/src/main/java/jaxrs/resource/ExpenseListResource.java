package jaxrs.resource;

import java.io.File;
import java.io.IOException;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
		User user = DAOProvider.getDAO().getUserByUsername(username);
		expenseList.setUserOwner(user);
		DAOProvider.getDAO().addExpenseList(expenseList);
		return expenseList.getName();
	}

	@DELETE
	@Path("/remove/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String removeExpenseListFromDatabase(@PathParam("name_expenseList") String name) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		DAOProvider.getDAO().removeExpenseListFromDatabase(expenseList);
		return "ok2";
	}

	@DELETE
	@Path("/removeCategory/{currentCategoryName}")
	@Produces({ MediaType.TEXT_PLAIN })
	public void removeExpenseCategoryFromDatabase(@PathParam("currentCategoryName") String name) {
		ExpenseCategory expenseCategory = DAOProvider.getDAO().getCategoryByName(name);
		expenseCategory.getExpenseListOwner().getExpenseCategories().remove(expenseCategory);
		DAOProvider.getDAO().removeExpenseCategoryFromDatabase(expenseCategory);
		for (ExpenseCategory category : expenseCategory.getSubCategories()) {
			// category.getExpenseListOwner().getExpenseCategories().remove(category);
			// DAOProvider.getDAO().removeExpenseCategoryFromDatabase(category);
			removeExpenseCategoryFromDatabase(category.getName());
		}
	}

	@POST
	@Path("/expenseCategory")
	@Produces({ MediaType.TEXT_PLAIN })
	public String editExpenseCategory(ExpenseCategory expenseCategory) {
		ExpenseCategory category = DAOProvider.getDAO().getCategoryByID(expenseCategory.getId());
		category.setName(expenseCategory.getName());
		category.issetFixed(expenseCategory.isgetFixed());
		return "SVE PET";
	}

	@POST
	@Path("/expenseItem")
	@Produces({ MediaType.TEXT_PLAIN })
	public String editExpenseItem(ExpenseItem expenseItem) {
		ExpenseItem item = DAOProvider.getDAO().getExpenseItemByID(expenseItem.getId());
		item.setComment(expenseItem.getComment());
		item.setName(expenseItem.getName());
		item.setEndDate(expenseItem.getEndDate());
		item.setStartDate(expenseItem.getStartDate());
		item.setPeriod(expenseItem.getPeriod());
		item.issetFixed(expenseItem.isFixed());
		return "SVE PET";
	}

	@POST
	@Path("/incomeItem")
	@Produces({ MediaType.TEXT_PLAIN })
	public String editIncomeItem(IncomeItem incomeItem) {
		IncomeItem item = DAOProvider.getDAO().getIncomeItemByID(incomeItem.getId());
		item.setComment(incomeItem.getComment());
		item.setName(incomeItem.getName());
		item.setEndDate(incomeItem.getEndDate());
		item.setStartDate(incomeItem.getStartDate());
		item.setPeriod(incomeItem.getPeriod());
		item.issetFixed(incomeItem.isFixed());
		return "SVE PET";
	}

	@POST
	@Path("/income/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String addIncomeItem(@PathParam("name_expenseList") String name, IncomeItem item) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		System.out.println(item);
		System.out.println(item.getAmounts().toString());
		expenseList.addNewIncomeItem(item);
		DAOProvider.getDAO().addIncomeItem(item);
		return "ok2";
	}

	@POST
	@Path("/category/{name_expenseList}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String addExpenseCategoryToExpenseList(@PathParam("name_expenseList") String name,
			ExpenseCategory expenseCategory) {
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		String catName = expenseCategory.getName().substring(0, expenseCategory.getName().indexOf('&'));
		String superCategoryName = expenseCategory.getName().substring(expenseCategory.getName().lastIndexOf("&") + 1);
		expenseCategory.setName(catName);
		if (!superCategoryName.equals("Default")) {
			ExpenseCategory superCategory = DAOProvider.getDAO().getCategoryByName(superCategoryName);
			expenseCategory.setSuperCategory(superCategory);
		}
		expenseCategory.setExpenseListOwner(expenseList);
		expenseList.addNewCategory(expenseCategory);

		DAOProvider.getDAO().addExpenseCategory(expenseCategory);
		return "ok1";
	}

	@POST
	@Path("/expenseitem/{category_name}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String addExpenseItemToExpenseList(@PathParam("category_name") String name, ExpenseItem expenseItem) {
		System.out.println(expenseItem);
		ExpenseCategory expenseCategory = DAOProvider.getDAO().getCategoryByName(name);
		System.out.println(expenseCategory);
		expenseItem.setExpenseCategoryOwner(expenseCategory);
		expenseItem.issetFixed(expenseCategory.isgetFixed());
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
	@Path("/expenselistTree/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ExpenseList getExpenseListByName(@PathParam("name") String name) {
		return DAOProvider.getDAO().getExpenseListByName(name);
	}

	@GET
	@Path("/expensecategory/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ExpenseCategory getExpenseCategoryByName(@PathParam("name") String name) {
		return DAOProvider.getDAO().getCategoryByName(name);
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

		PDDocument document = new PDDocument();
		PDFGenerator.generatePDF(document, username, expenseList);
		File file = new File("troskovnik.pdf");
		document.save(file);
		document.close();

		System.out.println("doso sam do tu");
		ResponseBuilder response = Response.ok((Object) file);
		// neznam da li ti treba onaj tamo zadnje desno .pdf
		response.header("Content-Disposition", "attachment; filename=troskovnik_" + expenseList.getName() + ".pdf");
		response.header("Content-Type", "application/force-download");
		return response.build();

	}

	
}
