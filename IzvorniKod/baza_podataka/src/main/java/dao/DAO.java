package dao;

import java.util.List;

import model.Notification;
import model.User;
import net.sf.ehcache.search.expression.Not;

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
	 *            username
	 * @return user with given username, or null if such does not exist
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

	/**
	 * Method used to add new notification to subsystem for data persistence.
	 * 
	 * @param notification
	 *            notification to be added
	 */
	public void addNotification(Notification notification);

	/**
	 * Method which returns {@link Notification} with given <code>id</code>. If
	 * such does not exists method returns <code>null</code>.
	 * 
	 * @param id
	 *            notification id
	 * @return notification with given <code>id</code>, or <code>null</code> if
	 *         such does not exist
	 */
	public Notification getNotification(Long id);

	/**
	 * Method used to remove notification wiht given<code>id</code> from the
	 * subsystem for data persistence. If such notification does not exists
	 * method throws {@link DAOException}
	 * 
	 * @param id
	 *            notification id
	 */
	public void removeNotification(Long id);

	/**
	 * Method used to retrieve all notifications binded to user with given
	 * <code>username</code>. If there are no notifications for that user method
	 * returns empty list. If there is no user with such <code>username</code>
	 * method throws {@link DAOException}
	 * 
	 * @param username
	 *            username
	 * @return list of notifications binded to user with given
	 *         <code>username</code>
	 */
	public List<Notification> getNotificationsForUser(String username);

	/**
	 * Method used to remove all notifications binded to user with given
	 * <code>username</code>. If there are no notifications for that user method
	 * does nothing.
	 * 
	 * @param username
	 *            username
	 */
	public void removeNotificationsForUser(String username);

}