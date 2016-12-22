package test;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import model.ExpenseCategory;
import model.ExpenseItem;
import model.ExpenseList;
import model.IncomeItem;
import model.User;
import model.time.Date;
import model.time.Period;

public class TroskovnikTest {

	private static User user = new User("nikola", "nikola", "Nikola", "Gajski", "12345", null, null, null,
			"nikola.gajski@fer.hr");
	
	private static ExpenseList expenseList = new ExpenseList(new Long(12345),"Troškovnik 2015",user);
	
	// troškovi
	private static ExpenseCategory nekretnine = new ExpenseCategory(new Long(1), "nekretnine",false, expenseList);
	private static ExpenseCategory pokretnine = new ExpenseCategory(new Long(2), "pokretnine",false, expenseList);
	
	private static ExpenseCategory rezije = new ExpenseCategory(new Long(3), "rezije",false, expenseList);
	private static ExpenseCategory hrana = new ExpenseCategory(new Long(4), "hrana",false, expenseList);
	
	private static ExpenseItem struja = new ExpenseItem(new Long(1),"struja", 
			new Date(2016,3), new Date(2017,3),Period.MONTHLY,"Režije struja",false);
	private static ExpenseItem voda = new ExpenseItem(new Long(2),"voda", 
			new Date(2016, 3), new Date(2017, 3),Period.QUARTARLY,"Režije voda",false);
	private static ExpenseItem kruh = new ExpenseItem(new Long(3),"kruh", 
			new Date(2016, 3), new Date(2017, 3),Period.MONTHLY,"Hrana kruh",false);
	private static ExpenseItem bojler = new ExpenseItem(new Long(4),"bojler", 
			new Date(2016,3), new Date(2017, 3),Period.ONE_TIME,"Režije bojler",false);
	private static ExpenseItem auto = new ExpenseItem(new Long(4),"auto", 
			new Date(2016, 3), new Date(2017, 3),Period.MONTHLY,"Troškovi auta",true);
	
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
	private static IncomeItem placa = new IncomeItem(new Long(1), "placa", new Date(2016, 3), new Date(2017, 3), null,
			Period.MONTHLY, true, "prihod od place", expenseList);
	private static IncomeItem ostaliPrihod = new IncomeItem(new Long(2), "ostali", new Date(2016, 1), new Date(2016, 12), null,
			Period.QUARTARLY, false, "prihod sa strane", expenseList);
	private static IncomeItem mobPrihod = new IncomeItem(new Long(3), "mob", new Date(2016, 5), new Date(2016, 12), null,
			Period.ONE_TIME, false, "prihod sa strane", expenseList);
	private static IncomeItem bubPrihod = new IncomeItem(new Long(4), "bubreg", new Date(2016, 6), new Date(2016, 12), null,
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
		Map<Date, Double> salaryIncomes = expenseList.findIncomeAmount(new Date(2016, 3),
				new Date(2017,3), true);
		Map<Date, Double> otherIncomes = expenseList.findIncomeAmount(new Date(2016, 3),
				new Date(2017, 3), false);
		Map<Date, Double> allIncomes = expenseList.findIncomeAmount(new Date(2016, 3),
				new Date(2017,3), null);
		
		System.out.println(salaryIncomes);
		System.out.println(otherIncomes);
		System.out.println(allIncomes);
		
		DefaultCategoryDataset graph1DataSet = new DefaultCategoryDataset();
		
		for (Map.Entry<Date, Double> entry : salaryIncomes.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
			graph1DataSet.addValue(value, "salary", xAxis);			
		}
		
		for (Map.Entry<Date, Double> entry : otherIncomes.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
			graph1DataSet.addValue(value, "other income", xAxis);			
		}
		
		for (Map.Entry<Date, Double> entry : allIncomes.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
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
		Map<Date, Double> fixedExpense = expenseList.findExpenseAmount(new Date(2016, 3),
				new Date(2017, 3), new LinkedHashMap<>(), expenseList.getExpenseCategories(), true);
		Map<Date, Double> variableExpense = expenseList.findExpenseAmount(new Date(2016,3),
				new Date(2017, 3), new LinkedHashMap<>(), expenseList.getExpenseCategories(), false);
		Map<Date, Double> allExpenses = expenseList.findExpenseAmount(new Date(2016,3),
				new Date(2017, 3), new LinkedHashMap<>(), expenseList.getExpenseCategories(), null);
		
		System.out.println(fixedExpense);
		System.out.println(variableExpense);
		System.out.println(allExpenses);
		
		DefaultCategoryDataset graf2DataSet = new DefaultCategoryDataset();
		
		for (Map.Entry<Date, Double> entry : fixedExpense.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
			graf2DataSet.addValue(value, "fixed expenses", xAxis);			
		}
		
		for (Map.Entry<Date, Double> entry : variableExpense.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
			graf2DataSet.addValue(value, "variable expenses", xAxis);			
		}
		
		for (Map.Entry<Date, Double> entry : allExpenses.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
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
		
		for (Map.Entry<Date, Double> entry : allIncomes.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
			grap3DataSet.addValue(value, "income", xAxis);			
		}
		
		for (Map.Entry<Date, Double> entry : allExpenses.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
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
	
	}

}
