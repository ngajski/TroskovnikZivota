package model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

public class ExpenseList {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private List<ExpenseItem> expenses;
	
	private List<IncomeItem> incomes;

	@ManyToMany
	private User owner;
	
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

	public List<ExpenseItem> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<ExpenseItem> expenses) {
		this.expenses = expenses;
	}

	public List<IncomeItem> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<IncomeItem> incomes) {
		this.incomes = incomes;
	}
	
}
