package model;


import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import model.time.Date;
import model.time.Period;

@Entity
@Table(name = "income_items")
public class IncomeItem extends Item {
	
	@ManyToOne
	private ExpenseList expenseListOwner;

	public IncomeItem() {
		super();
	}
	
	public IncomeItem(Long id, String name, Date startDate, Date endDate, List<Double> amounts, Period period,
			boolean fixed, String comment,ExpenseList expenseListOwner) {
		super(id, name, startDate, endDate, amounts, period, fixed, comment);
		this.expenseListOwner = expenseListOwner;
	}

	public ExpenseList getExpenseListOwner() {
		return expenseListOwner;
	}

	public void setExpenseListOwner(ExpenseList expenseListOwner) {
		this.expenseListOwner = expenseListOwner;
	}

	
}
