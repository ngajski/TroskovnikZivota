package jaxrs.resource;

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

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String addUser(User user) {
		System.out.println("Dodajem user u lokalnu" + user);
		DAOProvider.getDAO().addUser(user);
		return "ok";
	}

	@POST
	@Path("/validateLogin")
	@Produces({ MediaType.TEXT_PLAIN })
	public Boolean getUser(User user) {
		User userDB = DAOProvider.getDAO().getUserByUsername(user.getUsername());
		if (userDB == null || !userDB.getPassword().equals(user.getPassword())){
			return false;
		} else {
			return true;
		}
	}

}