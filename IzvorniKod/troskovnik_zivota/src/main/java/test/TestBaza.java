package test;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dao.DAOException;
import dao.jpa.JPAEMProvider;
import model.ExpenseList;
import model.User;

public class TestBaza {
	
	public static void main(String[] args) {
		User user = new User();
		user.setUsername("fic123");
		user.setPassword("123");
		user.setFirstName("Filip");
		user.setLastName("Bozic");
		user.setEmail("fbozic@gmail.com");
		
		ExpenseList expenseList = new ExpenseList();
		expenseList.setName("Troškovnik 2");
		expenseList.setUserOwner(user);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.lokalna");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(user);
		
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
		if (dex != null) {
			throw dex;
		}
		
		System.out.println("Dao user uspjesno");
		
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		em2.persist(expenseList);
		try {
			em2.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			em2.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		
		if (dex != null) {
			throw dex;
		}
	
		System.out.println("dao troskovnik uspjesno");
		emf.close();
	}

}
