package dao.jpa;

import javax.persistence.EntityManagerFactory;


/**
 * Class used to provide {@linkplain EntityManagerFactory}. Has to public
 * methods {@linkplain #setEmf(EntityManagerFactory)} and {@linkplain #getEmf()}
 * . First is used to set entity manager factory and second is used to provided
 * previously set entity manager factory. Method used to set entity manager
 * factory object is called from {@linkplain Initialization} class during
 * application boot and application shut down. Method for obtaining previously set
 * object is called from {@linkplain JPAEMProvider} class.
 * 
 * @author Filip
 * @version 1.0
 */
public class JPAEMFProvider{

	/**
	 * entity manager factory
	 */
	public static EntityManagerFactory emf;

	/**
	 * Method used to obtain entity manager factory.
	 * 
	 * @return entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Method used to set entity manager factory to given <code>emf</code>.
	 * 
	 * @param emf
	 *            entity manager factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}