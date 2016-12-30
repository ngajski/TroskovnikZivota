package jaxrs.resource;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.DAOProvider;
import dao.jpa.JPADAOImpl;
import model.ExpenseCategory;
import model.ExpenseItem;
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
		System.out.println("_________________________");
		System.out.println(name);
		User user = DAOProvider.getDAO().getUserByUsername(name);
		
		expenseList.revalidate(user);
		expenseList.setWritable(new Boolean(false));
		
//		DAOProvider.getDAO().addExpenseList(expenseList);
		return expenseList.getName();
	}
}
