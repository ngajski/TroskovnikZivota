package test;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dao.DAOException;
import model.ExpenseCategory;
import model.ExpenseItem;
import model.ExpenseList;
import model.IncomeItem;
import model.User;
import model.time.Period;

public class UbaciTroskovnik {

	private static User user = new User("test", "test", "Nikola", "Gajski", "12345", null, null, null,
			"nikola.gajski@test.hr");
	
	private static ExpenseList expenseList = new ExpenseList("Troškovnik_Rucno",user); // reverzni kraj postavljen
	
	// troškovi
	private static ExpenseCategory nekretnine = new ExpenseCategory("nekretnine",false, expenseList);
	private static ExpenseCategory pokretnine = new ExpenseCategory("pokretnine",false, expenseList);
	
	private static ExpenseCategory rezije = new ExpenseCategory("rezije",false, expenseList);
	private static ExpenseCategory hrana = new ExpenseCategory("hrana",false, expenseList);
	
	private static ExpenseItem struja = new ExpenseItem("struja", 
			"3/2016", "3/2017",Period.MONTHLY,"Režije struja",false);
	private static ExpenseItem voda = new ExpenseItem("voda", 
			"3/2016", "3/2017",Period.QUARTARLY,"Režije voda",false);
	private static ExpenseItem kruh = new ExpenseItem("kruh", 
			"3/2016", "3/2017",Period.MONTHLY,"Hrana kruh",false);
	private static ExpenseItem bojler = new ExpenseItem("bojler", 
			"3/2016", "3/2017",Period.ONE_TIME,"Režije bojler",false);
	private static ExpenseItem auto = new ExpenseItem("auto", 
			"3/2016", "3/2017",Period.MONTHLY,"Troškovi auta",true);
	
	private static List<Double> troskoviStruja = Arrays.asList(
			new Double(1000), new Double(1100.50),new Double(1250),
			new Double(1300),new Double(1400), new Double(1500),new Double(1600.13),
			new Double(1700),new Double(1800),new Double(1900),
			new Double(2000),new Double(2100),new Double(2200),new Double(2300),new Double(2400));
	
	private static List<Double> troskoviVoda = Arrays.asList(
			new Double(650),new Double(0),new Double(0),
			new Double(700),new Double(0),new Double(0),
			new Double(800),new Double(0),new Double(0),
			new Double(900.5),new Double(0),new Double(0),
			new Double(0),new Double(0));
	private static List<Double> troskoviBojler = Arrays.asList(new Double(5000));
	private static List<Double> troskoviAuto = Arrays.asList(new Double(3750.5));
	
	
	// prihodi
	private static IncomeItem placa = new IncomeItem( "placa", "3/2016", "3/2017", null,
			Period.MONTHLY, true, "prihod od place", expenseList);
	private static IncomeItem ostaliPrihod = new IncomeItem("ostali", "1/2016", "12/2016", null,
			Period.QUARTARLY, false, "prihod sa strane", expenseList);
	private static IncomeItem mobPrihod = new IncomeItem("mob","5/2016", "12/2016", null,
			Period.ONE_TIME, false, "prihod sa strane", expenseList);
	private static IncomeItem bubPrihod = new IncomeItem("bubreg", "6/2016","12/2016", null,
			Period.ONE_TIME, false, "prihod sa strane", expenseList);
	
	private static List<Double> prihodiOstali = Arrays.asList(
			new Double(5100), new Double(0),new Double(0),
			new Double(5400),new Double(0), new Double(0),
			new Double(5700),new Double(0),new Double(0),
			new Double(6100),new Double(0),new Double(0),
			new Double(6200),new Double(0),new Double(0),new Double(0),new Double(0));
	
	private static List<Double> prihodiPlaca = Arrays.asList(
			new Double(1000), new Double(2000),new Double(3000.5),
			new Double(4000),new Double(5000), new Double(6000),new Double(7000.13),
			new Double(8000),new Double(9000.6),new Double(10000),
			new Double(11000),new Double(12000),new Double(5400),new Double(5400),new Double(5500),
			new Double(5300),new Double(5300),new Double(5400),new Double(5400),new Double(5500));

	private static List<Double> prodanMobitel = Arrays.asList(new Double(4000));
	private static List<Double> prodanBubreg = Arrays.asList(new Double(6000));
	
	public static void main(String[] args) {
		
		expenseList.addNewCategory(nekretnine);
		expenseList.addNewCategory(pokretnine);
			
		nekretnine.addExpenseSubcategory(rezije);
		nekretnine.addExpenseSubcategory(hrana);
		
		rezije.addExpenseItem(struja);
		rezije.addExpenseItem(voda);
		rezije.addExpenseItem(bojler);	
		hrana.addExpenseItem(kruh);
		pokretnine.addExpenseItem(auto);
		
		struja.setAmounts(troskoviStruja);
		voda.setAmounts(troskoviVoda);
		kruh.setAmounts(troskoviStruja);
		bojler.setAmounts(troskoviBojler);
		auto.setAmounts(troskoviAuto);
		
		expenseList.addNewIncomeItem(bubPrihod);
		expenseList.addNewIncomeItem(placa);
		expenseList.addNewIncomeItem(ostaliPrihod);
		expenseList.addNewIncomeItem(mobPrihod);
		
		bubPrihod.setAmounts(prodanBubreg);
		mobPrihod.setAmounts(prodanMobitel);
		placa.setAmounts(prihodiPlaca);
		ostaliPrihod.setAmounts(prihodiOstali);
		
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.lokalna");
		DAOException dex = null;
		
		spremiUBazuUsera(emf, dex,user);
		
		spremiUBazuTroskovnik(emf, dex,expenseList);
		
		
		spremiUBazuKategoriju(emf, dex,pokretnine);
		spremiUBazuKategoriju(emf, dex,nekretnine);
		spremiUBazuKategoriju(emf, dex,hrana);
		spremiUBazuKategoriju(emf, dex,rezije);

		spremiUBazuStavku(emf, dex,struja);
		spremiUBazuStavku(emf, dex, voda);
		spremiUBazuStavku(emf, dex, bojler);
		spremiUBazuStavku(emf, dex, kruh);
		spremiUBazuStavku(emf, dex, auto);
		
		spremiUBazuPrihod(emf, dex, bubPrihod);
		spremiUBazuPrihod(emf, dex, placa);
		spremiUBazuPrihod(emf, dex, ostaliPrihod);
		spremiUBazuPrihod(emf, dex, mobPrihod);

		
		emf.close();
	}

	private static DAOException spremiUBazuTroskovnik(EntityManagerFactory emf, DAOException dex,ExpenseList expenseList) {
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		em2.persist(expenseList);
		try {
			em2.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			em2.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		
		if (dex != null) {
			throw dex;
		}
		
		System.out.println("dao troskovnik uspjesno");
		return dex;
	}

	private static DAOException spremiUBazuUsera(EntityManagerFactory emf, DAOException dex,User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		
		
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		if (dex != null) {
			throw dex;
		}
		
		System.out.println("Dao user uspjesno");
		return dex;
	}

	private static DAOException spremiUBazuKategoriju(EntityManagerFactory emf, DAOException dex,ExpenseCategory category) {
		EntityManager em5 = emf.createEntityManager();
		em5.getTransaction().begin();
		em5.persist(category);
		try {
			em5.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			em5.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		
		if (dex != null) {
			throw dex;
		}
	
		System.out.println("dao kategorija uspjesno");
		return dex;
	}

	private static void spremiUBazuStavku(EntityManagerFactory emf, DAOException dex,ExpenseItem item) {
		EntityManager em7 = emf.createEntityManager();
		em7.getTransaction().begin();
		em7.persist(item);
		try {
			em7.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			em7.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		
		if (dex != null) {
			throw dex;
		}
	
		System.out.println("dao stavka uspjesno");
	}
	
	private static DAOException spremiUBazuPrihod(EntityManagerFactory emf, DAOException dex,IncomeItem item) {
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		em2.persist(item);
		try {
			em2.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			em2.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		
		if (dex != null) {
			throw dex;
		}
		
		System.out.println("dao troskovnik uspjesno");
		return dex;
	}

}
