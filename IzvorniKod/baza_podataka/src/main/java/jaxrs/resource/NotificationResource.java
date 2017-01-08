package jaxrs.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.DAOProvider;
import model.Notification;
import model.User;

@Path("/notifications")
public class NotificationResource {

	@POST
	@Path("/{username}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String addNotification(@PathParam("username") String username, Notification notification) throws Exception {
		User user = DAOProvider.getDAO().getUserByUsername(username);
		notification.setUserOwner(user);
		DAOProvider.getDAO().addNotification(notification);
		return "ok";
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Notification getNotification(@PathParam("id") Long id) throws Exception {
		return DAOProvider.getDAO().getNotification(id);
	}

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String removeNotification(@PathParam("id") Long id) {
		DAOProvider.getDAO().removeNotification(id);
		return "ok";
	}

	@GET
	@Path("/all/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Notification> getNotificationsForUser(@PathParam("username") String username) {
		return DAOProvider.getDAO().getNotificationsForUser(username);
	}

	@DELETE
	@Path("/all/{username}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String removeNotificationsForUser(@PathParam("username") String username) {
		DAOProvider.getDAO().removeNotificationsForUser(username);
		return "ok";
	}
}