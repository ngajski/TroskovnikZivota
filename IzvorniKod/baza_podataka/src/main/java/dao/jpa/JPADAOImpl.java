package dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import dao.DAO;
import dao.DAOException;
import model.User;

/**
 * This class is implementation of {@linkplain DAO} interface and it's
 * functionallty is achieved using JPA technology.
 * 
 * @author Filip
 * @version 1.0
 */
public class JPADAOImpl implements DAO {

	@Override
	public void addUser(User user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(user);
	}
	
	@Override
	public User getUserByID(Long id) throws DAOException {
		User user = JPAEMProvider.getEntityManager().find(User.class, id);
		return user;
	}

	@Override
	public User getUserByUsername(String username) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			return (User) em.createQuery("select b from User as b where b.username=:username")
					.setParameter("username", username).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User getUserByEmail(String email) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			return (User) em.createQuery("select b from User as b where b.email=:email").setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public List<User> getUsersByUsername(String username) {
		return getUsersBySearchParamter("username", username);
	}

	@Override
	public List<User> getUsersByFirstName(String firstName) {
		return getUsersBySearchParamter("firstName", firstName);
	}

	@Override
	public List<User> getUsersByLastName(String lastName) {
		return getUsersBySearchParamter("lastName", lastName);

	}

	@Override
	public List<User> getUsersByEmail(String email) {
		return getUsersBySearchParamter("email", email);
	}

	private List<User> getUsersBySearchParamter(String searchParamter, String searchValue) {
		EntityManager em = JPAEMProvider.getEntityManager();
		return em.createQuery(" select b from User as b where b." + searchParamter + " LIKE :searchKeyword", User.class)
				.setParameter("searchKeyword", searchValue + "%").getResultList();
	}

}