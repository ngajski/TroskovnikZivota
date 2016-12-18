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
	 * Adds new blog user to subsystem for data persistence.
	 * 
	 * @param user
	 *            blog user to be added
	 * @throws DAOException
	 *             if error occurs during process of storing data
	 */
	public void addUser(User user) throws DAOException;

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
	 *            username
	 * @return user with given email, or null if such does not exist
	 * @throws DAOExceptionif
	 *             error occurs during process of data obtaining
	 */
	public User getUserByUsername(String username) throws DAOException;

	/**
	 * Method used to retrieve list of {@linkplain User}s whose username
	 * <b>starts</b> with given parameter <code>username</code>. If there are no
	 * such users method returns empty list.
	 * 
	 * @param username
	 *            username
	 * @return user with given email, or null if such does not exist
	 * @throws DAOExceptionif
	 *             error occurs during process of data obtaining
	 */
	public List<User> getUsersByUsername(String username);

	/**
	 * Method used to retrieve list of {@linkplain User}s whose first name
	 * <b>starts</b> with given parameter <code>firstName</code>. If there are
	 * no such users method returns empty list.
	 * 
	 * @param firstName
	 *            beginning of first name
	 * @return user with given email, or null if such does not exist
	 * @throws DAOExceptionif
	 *             error occurs during process of data obtaining
	 */
	public List<User> getUsersByFirstName(String firstName);

	/**
	 * Method used to retrieve list of {@linkplain User}s whose username
	 * <b>starts</b> with given parameter <code>username</code>. If there are no
	 * such users method returns empty list.
	 * 
	 * @param username
	 *            username
	 * @return user with given email, or null if such does not exist
	 * @throws DAOExceptionif
	 *             error occurs during process of data obtaining
	 */
	public List<User> getUsersByLastName(String lastName);

	/**
	 * Method used to retrieve list of {@linkplain User}s whose username
	 * <b>starts</b> with given parameter <code>username</code>. If there are no
	 * such users method returns empty list.
	 * 
	 * @param username
	 *            username
	 * @return user with given email, or null if such does not exist
	 * @throws DAOExceptionif
	 *             error occurs during process of data obtaining
	 */
	public List<User> getUsersByEmail(String email);

}