package jaxrs.resource;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	@Produces({ MediaType.TEXT_PLAIN})
	public String getServerTime() {
		return "received ping on " + new Date().toString();
	}
	
	@POST
	@Path("/store/{username}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String addExpenseList(@PathParam("username") String name,ExpenseList expenseList) {
		User user = DAOProvider.getDAO().getUserByUsername(name);
		
		expenseList.revalidate(user);
		expenseList.setWritable(new Boolean(false));
		expenseList.setId(null);
		
		DAOProvider.getDAO().addExpenseList(expenseList);
		return expenseList.getName();
	}
	
	@GET
	@Path("/check/{username}")
	@Produces({ MediaType.APPLICATION_JSON})
	public Boolean checkExpenseListForUsername(@PathParam("username") String username) {
		User user = DAOProvider.getDAO().getUserByUsername(username);
		
		if (user == null) {
			return false;
		}
		
		List<ExpenseList> expenseLists = user.getExpenseLists();
		
		if  (expenseLists != null && expenseLists.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@GET
	@Path("/get/{username}")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<ExpenseList> getExpenseListForUsername(@PathParam("username") String username) {
		User user = DAOProvider.getDAO().getUserByUsername(username);
		
		if (user == null) {
			return Collections.emptyList();
		} else {
			return user.getExpenseLists();
		}
	}
	
	@DELETE
	@Path("/remove/{name}")
	@Produces({ MediaType.TEXT_PLAIN})
	public String removeExpenseListFromDatabase(@PathParam("name") String name){
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(name);
		DAOProvider.getDAO().removeExpenseListFromDatabase(expenseList);
		return "Troskovnik maknut: " + name;
	}
}
