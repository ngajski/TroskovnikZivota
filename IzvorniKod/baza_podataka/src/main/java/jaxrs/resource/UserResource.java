package jaxrs.resource;

import java.util.Date;
import java.util.List;

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
	
//	@GET
//	@Path("/username/{username}")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public User getUserByUsername(@PathParam("username") String username) {
//		User user = DAOProvider.getDAO().getUserByUsername(username);
//		return user;
//	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String addUser(User user) throws Exception {
		Long existingUserId = user.getId();
		if (existingUserId != null) {
			User existingUser = DAOProvider.getDAO().getUserByID(existingUserId);
			existingUser.setUsername(user.getUsername());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setEmail(user.getEmail());
		} else {
			DAOProvider.getDAO().addUser(user);
		}
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

	
	//Methods used for searching through database.
	@GET
	@Path("username/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> usersByUsername(@PathParam("username") String username) {
		return DAOProvider.getDAO().getUsersByUsername(username);
	}

	@GET
	@Path("firstName/{firstName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> usersByFirstName(@PathParam("firstName") String firstName) {
		return DAOProvider.getDAO().getUsersByUsername(firstName);
	}
	
	@GET
	@Path("lastName/{lastName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> usersByLastName(@PathParam("lastName") String lastName) {
		return DAOProvider.getDAO().getUsersByUsername(lastName);
	}
	
	@GET
	@Path("email/{email}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> usersByEmail(@PathParam("email") String email) {
		return DAOProvider.getDAO().getUsersByUsername(email);
	}
}