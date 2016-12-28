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
			
			int payingMonth = Date.difference2(date,startDate);
			
			return amounts.get(payingMonth);
		} else {
			return new Double(0.0);
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

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
