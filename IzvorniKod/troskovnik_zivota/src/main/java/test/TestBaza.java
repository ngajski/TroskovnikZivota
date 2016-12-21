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
	
	private static User user = new User("nikola", "nikola", "Nikola", "Gajski", "12345", null, null, null,
			"nikola.gajski@fer.hr");
	
	private static ExpenseList expenseList = new ExpenseList(new Long(12345),"Troškovnik 2015",user);

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.lokalna");
		EntityManager em = emf.createEntityManager();
		
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
			dex.printStackTrace();
		}
		
		EntityManager em2 = emf.createEntityManager();
		
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
			dex.printStackTrace();
		}
	
	}

}
