package model;

import java.util.LinkedList;
import java.util.List;

public class ExpensesList {

	private List<Expens> listOfExpenses = new LinkedList<>();
	
	public ExpensesList(Expens... expenses) {
		super();
		for(Expens expens: expenses){
			this.listOfExpenses.add(expens);
		}
	}

	public List<Expens> getListOfExpenses() {
		return listOfExpenses;
	}

	public void setListOfExpenses(List<Expens> listOfExpenses) {
		this.listOfExpenses = listOfExpenses;
	}
	
}
