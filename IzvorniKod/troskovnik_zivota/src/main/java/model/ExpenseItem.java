package model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import model.time.Date;
import model.time.Period;


@Entity
@Table(name = "expense_items")
public class ExpenseItem {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;
	
	@ElementCollection	
	@CollectionTable(name ="expense_amounts")
	protected List<Double> amounts;
	
	@Enumerated(EnumType.STRING)
	protected Period period;
	
	@Column
	protected boolean fixed;
	
	@Column
	private String comment;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private ExpenseCategory expenseCategoryOwner;

	@Column
	private String startDate;
	
	@Column
	private String endDate;
	
	public ExpenseItem() {
		super();
	}
	
	public ExpenseItem(Long id, String name, List<Double> amounts, Period period,
			boolean fixed, String comment) {
		super();
		this.id = id;
		this.name = name;
		this.amounts = amounts;
		this.period = period;
		this.fixed = fixed;
		this.comment = comment;
		
	}	

	public ExpenseItem(Long id, String name, String startDate, String endDate, List<Double> amounts, Period period,
			boolean fixed, String comment, ExpenseCategory owner) {
		this(id, name, amounts, period, fixed, comment);
		
		this.endDate = endDate;
		this.startDate = startDate;
		this.expenseCategoryOwner = owner;
		
		if (period == Period.ONE_TIME) {
			endDate = startDate;
		}
	}
	
	public ExpenseItem(Long id, String name, String startDate, String endDate, Period period, String comment, boolean fixed) {
		this(id, name, startDate, endDate, new ArrayList<>(), period, fixed, comment, null);
	}

	public ExpenseItem(String name, String startDate, String endDate, Period period, String comment, boolean fixed) {
		this(null, name, startDate, endDate, new ArrayList<>(), period, fixed, comment, null);
	}


	/**
	 * Checks if owner category is fixed. If it is then this item is also fixed, else
	 * this item {@link #fixed} is determined with parameter given through 
	 * constructor.
	 * 
	 * @param fixed
	 * @return is it fixed
	 */
	public boolean checkOwnerCategory(boolean fixed) {
		if (expenseCategoryOwner != null && expenseCategoryOwner.isgetFixed()) {
			return true;
		} else {
			return fixed;
		}
	}
	
	/**
	 * Returns expense amount for selected month.
	 * 
	 * @param date selected {@link Date}
	 * @return expense amount {@link Double}
	 * 
	 */
	public Double getExpenseForDate(String date) {
		
		if (period == Period.ONE_TIME && date.equals(startDate)) {
			return amounts.get(0);
		} else if (period != Period.ONE_TIME && 
				Date.happenedAfter(date,startDate) && Date.happenedBefore(date,endDate)) {

			if (fixed) {
				return amounts.get(0);
			}
			
			int payingMonth = Date.difference2(this.startDate,date);
			
			List<Double> validatedAmounts = validateAmounts();
			return validatedAmounts.get(payingMonth);
		} else {
			return new Double(0.0);
		}
	}
	
	private List<Double> validateAmounts() {
		if (this.fixed) {
			return this.amounts;
		} else if (this.period == Period.MONTHLY) {
			return this.amounts;
		}
		
		List<Double> validatedAmounts = new ArrayList<>();
		int payingMonths = Date.difference2(startDate, endDate) + 1;
		
		int position = 0;
		if (period == Period.QUARTARLY) {
			for (int i = 0; i < payingMonths; ++i) {
				if (i % 3 == 0 && position < amounts.size()) {
					validatedAmounts.add(amounts.get(position));
					position++;
				} else {
					validatedAmounts.add(new Double(0));
				}
			}
		} else if (period == Period.ANUALY) {
			for (int i = 0; i < payingMonths; ++i) {
				if (i % 12 == 0 && position < amounts.size()) {
					validatedAmounts.add(amounts.get(position));
					position++;
				} else {
					validatedAmounts.add(new Double(0));
				}
			}
		} else {
			return this.amounts;
		}
		
		return validatedAmounts; 
		
	}
	
	/**
	 * Returns summed up income amounts between <code>startDate</code>
	 * and <code>endDate</code>
	 * 
	 * @param startDate
	 * @param stopDate
	 * @return summed up expense
	 */
	public Double getExpenseBetweenDates(String startDate,String stopDate) {
		double expense = 0;
		
		while (Date.happenedBefore(startDate, stopDate)) {
			expense += this.getExpenseForDate(startDate);
			startDate = Date.nextMonth(startDate);
		}
		
		return expense;
	}
	
	/**
	 * Sets <code>this.startDate</code> and <code>this.endDate</code> to right
	 * format.
	 */
	public void validateDates() {
		if (startDate != null && endDate != null) {
			if (this.startDate.startsWith("0")) {
				startDate = startDate.substring(1);
			}

			if (this.endDate.startsWith("0")) {
				endDate = endDate.substring(1);
			}
		}
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public ExpenseCategory getExpenseCategoryOwner() {
		return expenseCategoryOwner;
	}

	public void setExpenseCategoryOwner(ExpenseCategory expenseCategoryOwner) {
		this.expenseCategoryOwner = expenseCategoryOwner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getAmounts() {
		return amounts;
	}

	public void setAmounts(List<Double> amounts) {
		this.amounts = amounts;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void issetFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
	/**
	 * Sets this id to null and <code>expenseCategoryOwner<code>
	 * to given owner.
	 * 
	 * @param owner expenseList owner of this
	 */
	public void revalidate(ExpenseCategory owner) {
		this.id = null;
		this.expenseCategoryOwner = owner;
	}

	@Override
	public String toString() {
		return "ExpenseItem [id=" + id + ", name=" + name + ", amounts=" + amounts + ", period=" + period + ", fixed="
				+ fixed + ", comment=" + comment + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
}
