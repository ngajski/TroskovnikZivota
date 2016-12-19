package jaxrs.resource;

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

import dao.DAOProvider;
import model.ExpenseList;
import model.Period;
import wrappers.StringWrapper;
import model.User;

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
	public List<ExpenseList> getExpenseListsForUsername(@PathParam("username") String username) throws NamingException {
		return DAOProvider.getDAO().getExpenseListsForUsername(username);
	}

	@POST
	@Path("/{username}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String addExpenseListToUser(@PathParam("username") String username, ExpenseList expenseList) {
		System.out.println(expenseList.toString());

		User user = DAOProvider.getDAO().getUserByUsername(username);
		expenseList.setUserOwner(user);
		
		DAOProvider.getDAO().addExpenseList(expenseList);
		return "received ping on " + new Date().toString();
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

}