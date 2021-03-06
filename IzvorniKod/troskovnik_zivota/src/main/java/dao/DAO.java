package dao;

import java.util.List;

import model.ExpenseCategory;
import model.ExpenseItem;
import model.ExpenseList;
import model.IncomeItem;
import model.User;

/**
 * Interface towards subsystem for data persistence.
 * 
 * @author Filip
 * @version 1.0
 */
public interface DAO {

	/**
	 * Adds new blog user to subsystem for data persistence.
	 * 
	 * @param user
	 *            blog user to be added
	 * @throws DAOException
	 *             if error occurs during process of storing data
	 */
	public void addUser(User user) throws DAOException;

	/**
	 * Method which returns {@linkplain User} with given <code>id</code>. If
	 * such user does not exists method returns <code> null</code>.
	 * 
	 * @param id
	 *            user's id
	 * @return user with given id, or null if such does not exist
	 * @throws DAOException
	 *             if error occurs during process of data obtaining
	 */
	public User getUserByID(Long id) throws DAOException;

	/**
	 * Method which returns {@linkplain User} with given <code>email</code>. If
	 * such user does not exists method returns <code> null</code>.
	 * 
	 * @param email
	 *            email
	 * @return user with given email, or null if such does not exist
	 * @throws DAOExceptionif
	 *             error occurs during process of data obtaining
	 */
	public User getUserByEmail(String email) throws DAOException;

	/**
	 * Method which returns {@linkplain User} with given <code>username</code>.
	 * If such user does not exists method returns <code> null</code>.
	 * 
	 * @param username
	 *            user's username
	 * @return user with given username, or null if such does not exist
	 * @throws DAOException
	 *             if error occurs during process of data obtaining
	 */
	public User getUserByUsername(String username) throws DAOException;

	/**
	 * Method will return list of expense lists which belong to user with given
	 * <code>username</code>.
	 * 
	 * @param username
	 *            user's username
	 * @return list of expense lists
	 */
	public List<ExpenseList> getExpenseListsForUsername(String username);

	public void addExpenseList(ExpenseList expenseList);

	ExpenseList getExpenseListByName(String name);

	void addExpenseCategory(ExpenseCategory category);

	void removeExpenseListFromDatabase(ExpenseList expenseList);

	void addIncomeItem(IncomeItem item);

	ExpenseCategory getCategoryByName(String name) throws DAOException;

	void addExpenseItem(ExpenseItem expenseItem) throws DAOException;

	List<User> getUsers() throws DAOException;

	void removeExpenseCategory(ExpenseCategory expenseCategory);

	ExpenseCategory getCategoryByID(Long id) throws DAOException;

	ExpenseItem getExpenseItemByID(Long id) throws DAOException;

	IncomeItem getIncomeItemByID(Long id) throws DAOException;
	
	List<ExpenseCategory> getDirectExpenseCategories(ExpenseList expenseList); 

	void removeExpenseItem(ExpenseItem expenseItem);

	public void removeIncomeItem(IncomeItem item);

}