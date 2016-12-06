package dao.jpa;

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
	public User getUser(String username) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		try{
			return (User) em.createQuery("select b from User as b where b.username=:username")
					.setParameter("username", username)
					.getSingleResult();
			
		} catch (NoResultException e){
			return null;
		}
	}	

}