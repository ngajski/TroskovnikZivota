package model;

import java.util.List;

public class ExpenseListPravi {

	private String name;
	
	private List<ExpenseItem> expenses;
	
	private List<IncomeItem> incomes;

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
