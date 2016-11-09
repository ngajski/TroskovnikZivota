package dao;

import java.util.List;

import model.User;

/**
 * Interface towards subsystem for data persistence.
 * 
 * @author Filip
 * @version 1.0
 */
public interface DAO {
	
	/**
	 * Obtains user with given <code>username</code>. If such user does not
	 * exist it returns <code>null</code>.
	 * 
	 * @param username
	 *            user's username
	 * @return user or <code>null</code> if such does not exist
	 * @throws DAOException
	 *             if error occurs during process of data obtaining
	 */
	public User getUserByUsername(String username) throws DAOException;

	/**
	 * Obtains all users with given <code>nick</code>. If such user does not
	 * exist it returns <code>null</code>.
	 * 
	 * @return list of all users (if no users exist list is empty)
	 * @throws DAOException
	 *             if error occurs during process of data obtaining
	 */
	public List<User> getAllUsers() throws DAOException;
	
}