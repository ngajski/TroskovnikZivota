package model;


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
	
	public IncomeItem() {
		super();
	}
	
	public IncomeItem(Long id, String name, Date startDate, Date endDate, List<Double> amounts, Period period,
			boolean fixed, String comment,ExpenseList expenseListOwner) {
	super(id, name, amounts, period, fixed, comment);
		
		this.endDate = endDate;
		this.startDate = startDate;
		this.expenseListOwner = expenseListOwner;
		
		if (period == Period.ONE_TIME) {
			endDate = startDate;
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

	
}
