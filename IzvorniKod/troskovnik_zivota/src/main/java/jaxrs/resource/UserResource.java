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
	
	
	
	@POST
	@Path("/validateLogin")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public Boolean validateLogin(User user) throws Exception {
		System.out.println(user);
		User dbUser = 	DAOProvider.getDAO().getUserByUsername(user.getUsername());
		System.out.println(dbUser);
		if (dbUser == null || !dbUser.getPassword().equals(user.getPassword())){
			return false;
		} 
		return true;
	}

}