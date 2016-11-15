package jaxrs.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.DAO;
import dao.DaoUser;
import model.User;

@Path("/users")
public class UserResource {

	private static final DAO userDao = new DaoUser();
	private static final String SUCCESS_RESULT="<result>success</result>";
	private static final String FAILURE_RESULT="<result>failure</result>";

	@GET
    @Path("/ping")
    public String getServerTime() {
        System.out.println("RESTful Service 'UserService' is running ==> ping");
        return "received ping on "+new Date().toString();
    }
    
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(){
		System.out.println("users");
		return userDao.getAllUsers();
		
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username) {
		for (User user : userDao.getAllUsers()) {
			if (user.getUsername().equals(username)){
				return user;
			}
		}
		return null;
    }

}
