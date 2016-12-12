package jaxrs.resource;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.DAOProvider;
import model.ExpenseList;
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
	
	@GET
	@Path("/defaultCategories")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> getDefaultExpenseCategories() throws NamingException {
		List<String> defaultCategories = new LinkedList<>();
		defaultCategories.addAll(Arrays.asList("Nekretnine", "Hrana", "Pokretnine", "Kozmetika"));
		return defaultCategories;
	}

}