package model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import model.time.Date;
import model.time.Period;


@Entity
@Table(name = "expense_items")
public class ExpenseItem extends Item {

	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private ExpenseCategory expenseCategoryOwner;

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Date startDate;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Date endDate;
	
	public ExpenseItem() {
		super();
	}

	public ExpenseItem(Long id, String name, Date startDate, Date endDate, List<Double> amounts, Period period,
			boolean fixed, String comment, ExpenseCategory owner) {
		super(id, name, amounts, period, fixed, comment);
		
		this.endDate = endDate;
		this.startDate = startDate;
		this.expenseCategoryOwner = owner;
		
		if (period == Period.ONE_TIME) {
			endDate = startDate;
		}
	}
	
	public ExpenseItem(Long id, String name, Date startDate, Date endDate, Period period, String comment, boolean fixed) {
		this(id, name, startDate, endDate, new ArrayList<>(), period, fixed, comment, null);
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
		if (expenseCategoryOwner != null && expenseCategoryOwner.getFixed()) {
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
	public Double getExpenseForDate(Date date) {
		
		if (period == Period.ONE_TIME && date.equals(startDate)) {
			return amounts.get(0);
		} else if (period != Period.ONE_TIME && 
				date.happenedAfter(startDate) && date.happenedBefore(endDate)) {

			if (fixed) {
				return amounts.get(0);
			}
			
			int payingMonth = date.difference(startDate);
			
			return amounts.get(payingMonth);
		} else {
			return new Double(0.0);
		}
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public ExpenseCategory getExpenseCategoryOwner() {
		return expenseCategoryOwner;
	}

	public void setExpenseCategoryOwner(ExpenseCategory expenseCategoryOwner) {
		this.expenseCategoryOwner = expenseCategoryOwner;
	}
	
	
}
