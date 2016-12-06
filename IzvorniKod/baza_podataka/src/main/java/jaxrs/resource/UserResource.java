package jaxrs.resource;

import java.util.Date;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.User;

@Path("/users")
public class UserResource {

	private EntityManagerFactory entityManagerFactory;

	public UserResource(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

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
		EntityManager em = entityManagerFactory.createEntityManager();

		prepareTransaction(em);

		try {
			em.persist(user);
		} catch (Exception ex) {
			throw new DAOException("Could not store user", ex);
		}

		System.out.println("uspjesno dodao usera");

		finishTransaction(em);
		return "ok";
	}

	@GET
	@Path("/exists/username/{username}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String userExists(@PathParam("username") String username) throws NamingException {
		String answer = null;
		EntityManager em = entityManagerFactory.createEntityManager();

		prepareTransaction(em);

		System.out.println(username);
		try {
			em.createQuery("select b from User as b where b.username=:username").setParameter("username", username)
					.getSingleResult();
			answer = "true";
		} catch (NoResultException e) {
			answer = "false";
		}

		finishTransaction(em);

		System.out.println(answer);
		return answer;
	}

	@GET
	@Path("/exists/email/{email}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String emailExists(@PathParam("email") String email) throws NamingException {
		String answer = null;
		EntityManager em = entityManagerFactory.createEntityManager();

		prepareTransaction(em);

		try {
			em.createQuery("select b from User as b where b.email=:email").setParameter("email", email)
					.getSingleResult();
			answer = "true";
		} catch (NoResultException e) {
			answer = "false";
		}

		finishTransaction(em);

		System.out.println("email exists:" + answer);
		return answer;
	}

	private void prepareTransaction(EntityManager em) {
		try {
			em.getTransaction().begin();
		} catch (Exception ex) {
			throw new DAOException("Could not begin transaction.", ex);
		}

	}

	private void finishTransaction(EntityManager em) {
		DAOException dex = null;

		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}

		if (dex != null)
			throw dex;
	}

}