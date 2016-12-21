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
import model.User;
import model.time.Date;
import model.time.Period;

public class TroskovnikTest {

	private static User user = new User("nikola", "nikola", "Nikola", "Gajski", "12345", null, null, null,
			"nikola.gajski@fer.hr");
	
	private static ExpenseList expenseList = new ExpenseList(new Long(12345),"Troškovnik 2015",user);
	
	private static ExpenseCategory nekretnine = new ExpenseCategory(new Long(1), "nekretnine",false, expenseList);
	private static ExpenseCategory pokretnine = new ExpenseCategory(new Long(2), "pokretnine",false, expenseList);
	
	private static ExpenseCategory rezije = new ExpenseCategory(new Long(3), "rezije",false, expenseList);
	private static ExpenseCategory hrana = new ExpenseCategory(new Long(4), "hrana",false, expenseList);
	
	private static ExpenseItem struja = new ExpenseItem(new Long(1),"struja", 
			new Date(2016,1), new Date(2017,3),Period.MONTHLY,"Režije struja",false);
	private static ExpenseItem voda = new ExpenseItem(new Long(2),"voda", 
			new Date(2016, 1), new Date(2016, 12),Period.QUARTARLY,"Režije voda",false);
	private static ExpenseItem kruh = new ExpenseItem(new Long(3),"kruh", 
			new Date(2016, 1), new Date(2016, 11),Period.MONTHLY,"Hrana kruh",false);
	private static ExpenseItem bojler = new ExpenseItem(new Long(4),"bojler", 
			new Date(2016,1), new Date(2016, 10),Period.ONE_TIME,"Režije bojler",false);
	private static ExpenseItem auto = new ExpenseItem(new Long(4),"auto", 
			new Date(2016, 1), new Date(2017, 3),Period.MONTHLY,"Troškovi auta",true);
	
	private static List<Double> troskoviStruja = Arrays.asList(
			new Double(1200), new Double(1200.50),new Double(1350),
			new Double(1100),new Double(1300), new Double(1600),new Double(1400.13),
			new Double(1200),new Double(950.6),new Double(900),
			new Double(1350),new Double(1150),new Double(1250),new Double(1255),new Double(1350));
	
	private static List<Double> troskoviVoda = Arrays.asList(
			new Double(650),new Double(0),new Double(0),
			new Double(1200),new Double(0),new Double(0),
			new Double(1200),new Double(0),new Double(0),
			new Double(1000.5),new Double(0),new Double(0));
	private static List<Double> troskoviBojler = Arrays.asList(new Double(5000));
	private static List<Double> troskoviAuto = Arrays.asList(new Double(3750.5));
	

	public static void main(String[] args) {
		
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
		
		Map<Date, Double> fixedExpense = expenseList.findExpenseAmount(new Date(2016, 3),
				new Date(2017, 3), new LinkedHashMap<>(), expenseList.getExpenseCategories(), true);
		Map<Date, Double> variableExpense = expenseList.findExpenseAmount(new Date(2016,3),
				new Date(2017, 3), new LinkedHashMap<>(), expenseList.getExpenseCategories(), false);
		Map<Date, Double> allExpense = expenseList.findExpenseAmount(new Date(2016,3),
				new Date(2017, 3), new LinkedHashMap<>(), expenseList.getExpenseCategories(), null);
		
		System.out.println(fixedExpense);
		System.out.println(variableExpense);
		System.out.println(allExpense);
		
		DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
		
		for (Map.Entry<Date, Double> entry : fixedExpense.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
			line_chart_dataset.addValue(value, "fixed expenses", xAxis);			
		}
		
		for (Map.Entry<Date, Double> entry : variableExpense.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
			line_chart_dataset.addValue(value, "variable expenses", xAxis);			
		}
		
		for (Map.Entry<Date, Double> entry : allExpense.entrySet()) {
			Date date = entry.getKey();
			Double value = entry.getValue();
			String month = Date.getMonthName(date.getMonth());
			String xAxis = date.getYear() + "-" + month;
			line_chart_dataset.addValue(value, "all expenses", xAxis);			
		}

		
		JFreeChart lineChartObject = ChartFactory.createLineChart("Troškovi", "Vrijeme", "Trošak",
				line_chart_dataset, PlotOrientation.VERTICAL, true, true, false);

		int width = 1140; /* Width of the image */
		int height = 480; /* Height of the image */
		File lineChart = new File("LineChart.jpeg");
		
		try {
			ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
