package dao.jpa;

import javax.persistence.EntityManager;

import dao.DAOException;

/**
 * Class used to provide {@linkplain EntityManager} object. Class has two public
 * methods {@linkplain #getEntityManager()} and {@linkplain #close()}. First is
 * used to provided entity manager and second is used to close entity manager.
 * Second method is only called from class {@linkplain JPAFilter} when client's
 * request has been processed.
 * 
 * @author Filip
 * @version 1.0
 */
public class JPAEMProvider {

	/**
	 * object used to store {@linkplain LocalData} for single thread
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Method used to obtain {@linkplain EntityManager}. This method is called
	 * from servlets. First time it is called it will set entity manager
	 * provided via {@link JPAEMFProvider} class to internal map used to store
	 * local data and return it. After that each time <strong>same</strong>
	 * thread calls this method, method returns entity manager that has been
	 * previously stored.
	 * 
	 * @return entity manager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Method used to close {@linkplain EntityManager}. This method is only
	 * called from {@linkplain JPAFilter} class when client's request has been
	 * processed. If servlets that processed client request have not used entity
	 * manager method does nothing.
	 * 
	 * @throws DAOException
	 *             thrown if transaction is unable to commit or entity manager
	 *             is can not be closed
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * Class which models data that will be stored in {@linkplain ThreadLocal}
	 * object.
	 * 
	 * @author Filip
	 * @version 1.0
	 */
	private static class LocalData {
		/**
		 * entity manager
		 */
		EntityManager em;
	}

}