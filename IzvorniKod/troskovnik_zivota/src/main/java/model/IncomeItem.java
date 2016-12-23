package model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import model.time.Date;
import model.time.Period;

@Entity
@Table(name = "income_items")
public class IncomeItem extends Item {
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private ExpenseList expenseListOwner;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Date startDate;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Date endDate;
	
	@Column
	private boolean sallary;
	
	public IncomeItem() {
		super();
	}
	
	public IncomeItem(Long id, String name, Date startDate, Date endDate, List<Double> amounts, Period period,
			boolean sallary, String comment,ExpenseList expenseListOwner) {
	super(id, name, amounts, period, false, comment);
		
		this.endDate = endDate;
		this.startDate = startDate;
		this.expenseListOwner = expenseListOwner;
		this.sallary = sallary;
		
		if (period == Period.ONE_TIME) {
			endDate = startDate;
		}
	}
	
	/**
	 * Returns income amount for selected month.
	 * 
	 * @param date selected {@link Date}
	 * @return expense amount {@link Double}
	 * 
	 */
	public Double getIncomeForDate(Date date) {
		
		if (period == Period.ONE_TIME && date.equals(startDate)) {
			return amounts.get(0);
		} else if (period != Period.ONE_TIME && 
				date.happenedAfter(startDate) && date.happenedBefore(endDate)) {
			
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

	public ExpenseList getExpenseListOwner() {
		return expenseListOwner;
	}

	public void setExpenseListOwner(ExpenseList expenseListOwner) {
		this.expenseListOwner = expenseListOwner;
	}

	public boolean isSallary() {
		return sallary;
	}

	public void setSallary(boolean sallary) {
		this.sallary = sallary;
	}
	
	@Override
	public String toString() {
		return this.getName() + " " + this.getComment();
	}


	
}
