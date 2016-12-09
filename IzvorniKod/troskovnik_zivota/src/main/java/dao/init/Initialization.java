package dao.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.jpa.JPAEMFProvider;



/**
 * This class is used to create {@linkplain EntityManagerFactory}, which is
 * connected to our local database, during application boot up. Newly create
 * entity manager factory is than used to create entity managers for servlets
 * that wish to communicate with subsytem for data persistence.
 * <p>
 * During application shot down class will close create entity manager factory
 * so their is no resources leak.
 * 
 * @author Filip
 * @version 1.0
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.lokalna");
		sce.getServletContext().setAttribute("emf", emf);
		JPAEMFProvider.setEmf(emf);
		
		System.out.println("init local emf");
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("emf");
		if (emf != null) {
			emf.close();
		}
		
		System.out.println("closing local emf");
	}
}