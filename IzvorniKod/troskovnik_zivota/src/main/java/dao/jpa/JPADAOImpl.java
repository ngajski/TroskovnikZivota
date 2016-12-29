package dao.jpa;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import dao.DAO;
import dao.DAOException;
import model.ExpenseCategory;
import model.ExpenseItem;
import model.ExpenseList;
import model.IncomeItem;
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
	public List<User> getUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<User> list = em.createQuery("select b from User as b").getResultList();

		return list;
	}

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
	public ExpenseCategory getCategoryByName(String name) throws DAOException{
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			return (ExpenseCategory) em.createQuery("select b from ExpenseCategory as b where b.name=:name")
					.setParameter("name", name).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}
	@Override
	public void addExpenseItem(ExpenseItem expenseItem) throws DAOException{
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(expenseItem);
	}
	
	@Override
	public void removeExpenseListFromDatabase(ExpenseList expenseList){
		EntityManager em = JPAEMProvider.getEntityManager();
		em.remove(expenseList);
	}
	@Override
	public User getUserByUsername(String username) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		System.out.println("getUserByUsername:  " + Thread.currentThread());
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
	public ExpenseList getExpenseListByName(String name){
		EntityManager em = JPAEMProvider.getEntityManager();	
		try{
			return (ExpenseList) em.createQuery("select b from ExpenseList as b where b.name=:name").setParameter("name", name).getSingleResult();
		}
		catch (NoResultException e){
			return null;
		}
	}

	@Override
	public void addExpenseList(ExpenseList expenseList) {
		EntityManager em = JPAEMProvider.getEntityManager();
		System.out.println("addExpenseList:  " + Thread.currentThread());
		em.persist(expenseList);
	}
	
	@Override
	public void addExpenseCategory(ExpenseCategory category){
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(category);
	}
	
	@Override
	public void addIncomeItem(IncomeItem item){
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(item);
	}

	@Override
	public List<ExpenseList> getExpenseListsForUsername(String username) {
		User user = getUserByUsername(username);
		if (user == null){
			return Collections.emptyList();
		} else {
			return user.getExpenseLists();
		}
	}





}