package jaxrs.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import dao.DAOProvider;
import model.User;

@Path("/users")
public class UserResource {

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String addUser(User user) throws Exception {
		DAOProvider.getDAO().addUser(user);

		return "ok";
	}

}