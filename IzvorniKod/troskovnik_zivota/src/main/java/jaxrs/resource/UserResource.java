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

	@GET
	@Path("/username/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public User getUserByUsername(@PathParam("username") String username) {
		User user = DAOProvider.getDAO().getUserByUsername(username);
		System.out.println(user);
		System.out.println(user.getAddress());
		return user;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String addUser(User user) {
		System.out.println(user.toString());
		Long existingUserId = user.getId();
		if (existingUserId != null) {
			User existingUser = DAOProvider.getDAO().getUserByID(existingUserId);
			existingUser.setUsername(user.getUsername());
			existingUser.setPassword(user.getPassword());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setEmail(user.getEmail());
			existingUser.setTelefon(user.getTelefon());
			existingUser.setOib(user.getOib());
			existingUser.setDateOfBirth(user.getDateOfBirth());
			existingUser.setAddress(user.getAddress());
		} else {
			DAOProvider.getDAO().addUser(user);
		}
		return "ok";
	}
	
	@POST
	@Path("/validateLogin")
	@Produces({ MediaType.TEXT_PLAIN })
	public Boolean getUser(User user) {
		User userDB = DAOProvider.getDAO().getUserByUsername(user.getUsername());
		if (userDB == null || !userDB.getPassword().equals(user.getPassword())) {
			return false;
		} else {
			return true;
		}
	}

}