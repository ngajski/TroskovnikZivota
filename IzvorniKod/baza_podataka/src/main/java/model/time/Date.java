package model.time;

import java.util.Arrays;
import java.util.List;

/**
 * Class with static methods for operations with dates given in format:
 * MM/YYYY
 * 
 * @author nikola
 *
 */
public class Date {
	
	private static final List<String> MONTHS = Arrays.asList("January", "February", "March","April","May",
			"June","July","August","October","September","November","December");
		
	/**
	 * Returns {@link String} representation of month given with
	 * number from 1 - 12.
	 * 
	 * @param monthNumber int from 1 - 12
	 * @return {@link String} representation of month
	 */
	public static String getMonthName(int monthNumber) {
		if (monthNumber < 1 || monthNumber > 12) {
			return null;
		}
		return MONTHS.get(monthNumber-1);
	}

	/**
	 * Returns true if date1 happened after date2 or in this month.
	 * Date format: mm/yyyy
	 * 
	 * 
	 * @return true if this happened after givent date
	 */
	public static boolean happenedAfter(String date1,String date2) {
		String[] polje1 = date1.split("/");
		String[] polje2 = date2.split("/");
		
		int year1 = Integer.parseInt(polje1[1]);
		int year2 = Integer.parseInt(polje2[1]);
		int month1 = Integer.parseInt(polje1[0]);
		int month2 = Integer.parseInt(polje2[0]);
		
		if (year1 > year2) {
			return true;
		} else if (year1 < year2) {
			return false;
		} else if (month1 >= month2) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if date1 happened before given date or in this month.
	 * 
	 * @return true if this happened before given date
	 */
	public static boolean happenedBefore(String date1,String date2) {
		String[] polje1 = date1.split("/");
		String[] polje2 = date2.split("/");
		
		int year1 = Integer.parseInt(polje1[1]);
		int year2 = Integer.parseInt(polje2[1]);
		int month1 = Integer.parseInt(polje1[0]);
		int month2 = Integer.parseInt(polje2[0]);
		
		if (year1 < year2) {
			return true;
		} else if (year1 > year2) {
			return false;
		}else if (month1 <= month2) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns difference in months between date1 and date2.
	 * 
	 * @param date {@link Date}
	 * @return difference in months
	 * 
	 * 
	 */
	public static int difference2(String date1,String date2) {
		String[] polje1 = date1.split("/");
		String[] polje2 = date2.split("/");
		
		int year1 = Integer.parseInt(polje1[1]);
		int year2 = Integer.parseInt(polje2[1]);
		int month1 = Integer.parseInt(polje1[0]);
		int month2 = Integer.parseInt(polje2[0]);
		
		int yearDif = Math.abs(year1 - year2);
		int monthDif = month1 - month2;
		return yearDif * 12 + monthDif;
	}
	
	/**
	 * Switch from current month to next month, and returns next month in 
	 * {@link String} representation: mm/yyyy
	 */
	public static String nextMonth(String date) {
		String[] polje = date.split("/");
		
		int year = Integer.parseInt(polje[1]);
		int month = Integer.parseInt(polje[0]);
		
		if (month < 12) {
			month++;
		} else {
			year++;
			month = new Integer(1);
		}
		
		return month + "/" + year;
	}
	
	public static int getMonth(String date) {
		return Integer.parseInt(date.split("/")[0]);
	}
	
	
}
