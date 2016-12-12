package jaxrs.resource;

import java.util.Date;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.DAOProvider;
import model.User;

@Path("/users")
public class UserResource {

	@GET
	@Path("/ping")
	public String getServerTime() {
		System.out.println("RESTful Service 'UserService' is running ==> ping");
		return "received ping on " + new Date().toString();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String addUser(User user) throws Exception {
		System.out.println("Dodajem user u udaljenu: " + user);
		DAOProvider.getDAO().addUser(user);
		return "ok";
	}

	@GET
	@Path("/exists/username/{username}")
	@Produces({ MediaType.TEXT_PLAIN })
	public Boolean userExists(@PathParam("username") String username) {
		User user = DAOProvider.getDAO().getUserByUsername(username);
		System.out.println(user);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@GET
	@Path("/exists/email/{email}")
	@Produces({ MediaType.TEXT_PLAIN })
	public Boolean emailExists(@PathParam("email") String email) {
		User user = DAOProvider.getDAO().getUserByEmail(email);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

}