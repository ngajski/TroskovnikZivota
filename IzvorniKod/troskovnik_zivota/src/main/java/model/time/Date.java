package model.time;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import model.ExpenseItem;
import model.IncomeItem;

@Entity
@Table(name = "dates")
public class Date {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name ="\"year\"")
	private Integer year;
	
	@Column
	private Integer month;
	
	@JsonIgnore
	@OneToMany(mappedBy = "startDate", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<ExpenseItem> startDateExpenseOwners;
	
	@JsonIgnore
	@OneToMany(mappedBy = "endDate", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<ExpenseItem> endDateExpenseOwners;
	
	@JsonIgnore
	@OneToMany(mappedBy = "startDate", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<IncomeItem> startDateIncomeOwners;
	
	@JsonIgnore
	@OneToMany(mappedBy = "endDate", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<IncomeItem> endDateIncomeOwners;
	
	private static final List<String> MONTHS = Arrays.asList("January", "February", "March","April","May",
			"June","July","August","October","September","November","December");
	
	public Date() {
		super();
	}
	
	public Date(int year, int month) {
		if (month < 1 || month > 12) {
			throw new IllegalArgumentException("Month outside of interval: " + month);
		}
		this.year = year;
		this.month = month;
	}
	
	public Date(Date date) {
		this.month = date.getMonth();
		this.year = date.getYear();
	}
	
	/**
	 * Returns true if this happened after given date or in this month.
	 * 
	 * @param date {@link Date}
	 * @return true if this happened after givent date
	 */
	public boolean happenedAfter(Date date) {
		if (this.year > date.getYear()) {
			return true;
		} else if (this.year < date.getYear()) {
			return false;
		} else if (this.month >= date.getMonth()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if this happened before given date or in this month.
	 * 
	 * @param date {@link Date}
	 * @return true if this happened before given date
	 */
	public boolean happenedBefore(Date date) {
		if (this.year < date.getYear()) {
			return true;
		} else if (this.year > date.getYear()) {
			return false;
		}else if (this.month <= date.getMonth()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns difference in months between given {@link Date} and this.
	 * 
	 * @param date {@link Date}
	 * @return difference in months
	 * 
	 * 
	 */
	public int difference(Date date) {
		int yearDif = Math.abs(this.year - date.year);
		int monthDif = this.month - date.month;
		return yearDif * 12 + monthDif;
	}
	
	/**
	 * Switch from current month to next month.
	 */
	public void nextMonth() {
		if (month < 12) {
			month++;
		} else {
			year++;
			month = new Integer(1);
		}
	}
	
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

	public Integer getMonth() {
		return month;
	}
	
	public Integer getYear() {
		return year;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Date other = (Date) obj;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Date: " + year + " - " + month;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public List<ExpenseItem> getStartDateExpenseOwners() {
		return startDateExpenseOwners;
	}

	public void setStartDateExpenseOwners(List<ExpenseItem> startDateExpenseOwners) {
		this.startDateExpenseOwners = startDateExpenseOwners;
	}

	public List<ExpenseItem> getEndDateExpenseOwners() {
		return endDateExpenseOwners;
	}

	public void setEndDateExpenseOwners(List<ExpenseItem> endDateExpenseOwners) {
		this.endDateExpenseOwners = endDateExpenseOwners;
	}

	public List<IncomeItem> getStartDateIncomeOwners() {
		return startDateIncomeOwners;
	}

	public void setStartDateIncomeOwners(List<IncomeItem> startDateIncomeOwners) {
		this.startDateIncomeOwners = startDateIncomeOwners;
	}

	public List<IncomeItem> getEndDateIncomeOwners() {
		return endDateIncomeOwners;
	}

	public void setEndDateIncomeOwners(List<IncomeItem> endDateIncomeOwners) {
		this.endDateIncomeOwners = endDateIncomeOwners;
	}
	
	
}
