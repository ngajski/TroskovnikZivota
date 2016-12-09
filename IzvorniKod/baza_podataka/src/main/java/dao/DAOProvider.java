package dao;

import dao.jpa.JPADAOImpl;

/**
 * Singleton class which knows what needs to be returned as service provider for
 * access to subystem for data persistence.
 
 * @author Filip
 *
 */
public class DAOProvider {

	/**
	 * {@linkplain DAO} object
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Method used to obtain {@linkplain DAO} object.
	 * 
	 * @return object which encapsulates access to subsystem for data
	 *         persistence.
	 */
	public static DAO getDAO() {
		return dao;
	}

}