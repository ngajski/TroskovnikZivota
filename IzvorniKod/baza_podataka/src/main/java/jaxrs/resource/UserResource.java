package jaxrs.resource;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.User;

@Path("/users")
public class UserResource {

	private EntityManager em;

	protected EntityManager getEntityManager() throws NamingException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.udaljena");
		return emf.createEntityManager();
	}

	@GET
	@Path("/ping")
	public String getServerTime() {
		System.out.println("RESTful Service 'UserService' is running ==> ping");
		return "received ping on " + new Date().toString();
	}

	@GET
	@Path("/exists/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@PathParam("username") String username) throws NamingException {
		String answer = null;

		try {
			em = getEntityManager();
			em.getTransaction().begin();

			em.createQuery("select b from User as b where b.username=:username").setParameter("username", username)
					.getSingleResult();

			em.getTransaction().commit();
			em.close();
			
			answer = "true";
		} catch (NoResultException e) {
			answer = "false";
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(answer);
		return answer;
	}

}