package model;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import model.time.Date;
import model.time.Period;


@Entity
@Table(name = "expenses_items")
public class ExpenseItem extends Item {

	@ManyToOne
	private ExpenseCategory expenseCategoryOwner;

	public ExpenseItem() {
		super();
	}

	
	
	public ExpenseItem(Long id, String name, Date startDate, Date endDate, List<Double> amounts, Period period,
			boolean fixed, String comment, ExpenseCategory owner) {
		super(id, name, startDate, endDate, amounts, period, fixed, comment);
		
		expenseCategoryOwner = owner;
	}
	
	public ExpenseItem(Long id, String name, Date startDate, Date endDate, Period period, String comment, boolean fixed) {
		super(id, name, startDate, endDate, new ArrayList<>(), period, fixed, comment);
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
		if (expenseCategoryOwner != null && expenseCategoryOwner.isFixed()) {
			return true;
		} else {
			return fixed;
		}
	}
	
	public ExpenseCategory getExpenseCategoryOwner() {
		return expenseCategoryOwner;
	}

	public void setExpenseCategoryOwner(ExpenseCategory expenseCategoryOwner) {
		this.expenseCategoryOwner = expenseCategoryOwner;
	}
	
	
}
