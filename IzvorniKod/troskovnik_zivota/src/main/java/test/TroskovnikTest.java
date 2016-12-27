package test;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import dao.DAOException;
import model.ExpenseCategory;
import model.ExpenseItem;
import model.ExpenseList;
import model.IncomeItem;
import model.User;
import model.time.Date;
import model.time.Period;

public class TroskovnikTest {

	private static User user = new User("nikola2", "nikola2", "Nikola", "Gajski", "12345", null, null, null,
			"nikola.gajski@fer.hr");
	
	private static ExpenseList expenseList = new ExpenseList("Troškovnik 2016",user);
	
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
		
		// prihodi
		expenseList.addNewIncomeItem(bubPrihod);
		expenseList.addNewIncomeItem(placa);
		expenseList.addNewIncomeItem(ostaliPrihod);
		expenseList.addNewIncomeItem(mobPrihod);
		
		bubPrihod.setAmounts(prodanBubreg);
		mobPrihod.setAmounts(prodanMobitel);
		placa.setAmounts(prihodiPlaca);
		ostaliPrihod.setAmounts(prihodiOstali);
		
		// troškovi
		expenseList.addNewCategory(nekretnine);
		expenseList.addNewCategory(pokretnine);
		
		nekretnine.addExpenseSubcategory(rezije);
		nekretnine.addExpenseSubcategory(hrana);
		
		rezije.addExpenseItem(struja);
		rezije.addExpenseItem(voda);
		hrana.addExpenseItem(kruh);
		rezije.addExpenseItem(bojler);
		
		struja.setAmounts(troskoviStruja);
		voda.setAmounts(troskoviVoda);
		kruh.setAmounts(troskoviStruja);
		bojler.setAmounts(troskoviBojler);
		
		pokretnine.addExpenseItem(auto);
		auto.setAmounts(troskoviAuto);
		
		// Graf 1
		Map<String, Double> salaryIncomes = expenseList.findIncomeAmount("3/2016",
				"3/2017", true);
		Map<String, Double> otherIncomes = expenseList.findIncomeAmount("3/2016",
				"3/2017", false);
		Map<String, Double> allIncomes = expenseList.findIncomeAmount("3/2016",
				"3/2017", null);
		
		System.out.println(salaryIncomes);
		System.out.println(otherIncomes);
		System.out.println(allIncomes);
		
		DefaultCategoryDataset graph1DataSet = new DefaultCategoryDataset();
		
		for (Map.Entry<String, Double> entry : salaryIncomes.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis =	date;
			graph1DataSet.addValue(value, "salary", xAxis);			
		}
		
		for (Map.Entry<String, Double> entry : otherIncomes.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis =	date;
			graph1DataSet.addValue(value, "oher incomes", xAxis);			
		}
		
		for (Map.Entry<String, Double> entry : allIncomes.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis =	date;
			graph1DataSet.addValue(value, "all incomes", xAxis);			
		}

		
		JFreeChart graph1 = ChartFactory.createLineChart("Prihodi", "Vrijeme", "Prihod",
				graph1DataSet, PlotOrientation.VERTICAL, true, true, false);

		int width = 1140; /* Width of the image */
		int height = 480; /* Height of the image */
		File lineChart = new File("Graf1_prihod.jpeg");
		
		try {
			ChartUtilities.saveChartAsJPEG(lineChart, graph1, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// Graf 1 kraj
		
		
		// Graf 2
		Map<String, Double> fixedExpense = expenseList.findExpenseAmount("3/2016",
				"3/2017", new LinkedHashMap<>(), expenseList.getExpenseCategories(), true);
		Map<String, Double> variableExpense = expenseList.findExpenseAmount("3/2016",
				"3/2017", new LinkedHashMap<>(), expenseList.getExpenseCategories(), false);
		Map<String, Double> allExpenses = expenseList.findExpenseAmount("3/2016",
				"3/2017", new LinkedHashMap<>(), expenseList.getExpenseCategories(), null);
		
		System.out.println(fixedExpense);
		System.out.println(variableExpense);
		System.out.println(allExpenses);
		
		DefaultCategoryDataset graf2DataSet = new DefaultCategoryDataset();
		
		for (Map.Entry<String, Double> entry : fixedExpense.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis = date;
			graf2DataSet.addValue(value, "fixed expenses", xAxis);			
		}
		
		for (Map.Entry<String, Double> entry : variableExpense.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis = date;
			graf2DataSet.addValue(value, "variable expenses", xAxis);			
		}
		
		for (Map.Entry<String, Double> entry : allExpenses.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis = date;
			graf2DataSet.addValue(value, "all expenses", xAxis);			
		}

		
		JFreeChart lineChartObject = ChartFactory.createLineChart("Troškovi", "Vrijeme", "Trošak",
				graf2DataSet, PlotOrientation.VERTICAL, true, true, false);

		width = 1140; /* Width of the image */
		height = 480; /* Height of the image */
		File lineChart2 = new File("Graf2_trošak.jpeg");
		
		try {
			ChartUtilities.saveChartAsJPEG(lineChart2, lineChartObject, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Graf 3 
		
		DefaultCategoryDataset grap3DataSet = new DefaultCategoryDataset();
		
		for (Map.Entry<String, Double> entry : allIncomes.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis = date;
			grap3DataSet.addValue(value, "income", xAxis);			
		}
		
		for (Map.Entry<String, Double> entry : allExpenses.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis = date;
			grap3DataSet.addValue(value, "expense", xAxis);			
		}

		
		JFreeChart graph3 = ChartFactory.createLineChart("Ukupno", "Vrijeme", "Novac",
				grap3DataSet, PlotOrientation.VERTICAL, true, true, false);

		File lineChart3 = new File("Graf3_ukupno.jpeg");
		
		try {
			ChartUtilities.saveChartAsJPEG(lineChart3, graph3, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
//	
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.lokalna");
//		DAOException dex = null;
		
//		EntityManager em = emf.createEntityManager();
//		em.getTransaction().begin();
//		em.persist(user);
//		
//		
//		try {
//			em.getTransaction().commit();
//		} catch (Exception ex) {
//			dex = new DAOException("Unable to commit transaction.", ex);
//		}
//		
//		try {
//			em.close();
//		} catch (Exception ex) {
//			if (dex != null) {
//				dex = new DAOException("Unable to close entity manager.", ex);
//			}
//		}
//		if (dex != null) {
//			throw dex;
//		}
//		
//		System.out.println("Dao user uspjesno");
//		
//		EntityManager em2 = emf.createEntityManager();
//		em2.getTransaction().begin();
//		em2.persist(expenseList);
//		try {
//			em2.getTransaction().commit();
//		} catch (Exception ex) {
//			dex = new DAOException("Unable to commit transaction.", ex);
//		}
//		
//		try {
//			em2.close();
//		} catch (Exception ex) {
//			if (dex != null) {
//				dex = new DAOException("Unable to close entity manager.", ex);
//			}
//		}
//		
//		if (dex != null) {
//			throw dex;
//		}
//	
//		System.out.println("dao troskovnik uspjesno");
		
//		EntityManager em3 = emf.createEntityManager();
//		em3.getTransaction().begin();
//		em3.persist(bubPrihod);
//		try {
//			em3.getTransaction().commit();
//		} catch (Exception ex) {
//			dex = new DAOException("Unable to commit transaction.", ex);
//		}
//		
//		try {
//			em3.close();
//		} catch (Exception ex) {
//			if (dex != null) {
//				dex = new DAOException("Unable to close entity manager.", ex);
//			}
//		}
//		
//		if (dex != null) {
//			throw dex;
//		}
	
//		System.out.println("bubPrihod uspjesno");
//		
//		EntityManager em4 = emf.createEntityManager();
//		em4.getTransaction().begin();
//		em4.persist(placa);
//		try {
//			em4.getTransaction().commit();
//		} catch (Exception ex) {
//			dex = new DAOException("Unable to commit transaction.", ex);
//		}
//		
//		try {
//			em4.close();
//		} catch (Exception ex) {
//			if (dex != null) {
//				dex = new DAOException("Unable to close entity manager.", ex);
//			}
//		}
//		
//		if (dex != null) {
//			throw dex;
//		}
//	
//		System.out.println("placa uspjesno");
//		
//		EntityManager em5 = emf.createEntityManager();
//		em5.getTransaction().begin();
//		em5.persist(ostaliPrihod);
//		try {
//			em5.getTransaction().commit();
//		} catch (Exception ex) {
//			dex = new DAOException("Unable to commit transaction.", ex);
//		}
//		
//		try {
//			em5.close();
//		} catch (Exception ex) {
//			if (dex != null) {
//				dex = new DAOException("Unable to close entity manager.", ex);
//			}
//		}
//		
//		if (dex != null) {
//			throw dex;
//		}
//		System.out.println("ostaliPrihod uspjesno");
//		
//		EntityManager em6 = emf.createEntityManager();
//		em6.getTransaction().begin();
//		em6.persist(mobPrihod);
//		try {
//			em6.getTransaction().commit();
//		} catch (Exception ex) {
//			dex = new DAOException("Unable to commit transaction.", ex);
//		}
//		
//		try {
//			em6.close();
//		} catch (Exception ex) {
//			if (dex != null) {
//				dex = new DAOException("Unable to close entity manager.", ex);
//			}
//		}
//		
//		if (dex != null) {
//			throw dex;
//		}
//	
//		System.out.println("mobPrihod uspjesno");
//		
//		emf.close();
	}

}
