package dao;

import model.User;


/**
 * Interface towards subsystem for data persistence.
 * 
 * @author Filip
 * @version 1.0
 */
public interface DAO {

	public User getUser(String username) throws DAOException;
	
	/**
	 * Adds new blog user to subsystem for data persistence.
	 * 
	 * @param user
	 *            blog user to be added
	 * @throws DAOException
	 *             if error occurs during process of data obtaining
	 */
	public void addUser(User user) throws DAOException;

}