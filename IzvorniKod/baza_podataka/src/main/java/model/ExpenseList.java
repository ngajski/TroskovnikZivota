package model;

import java.util.LinkedList;
import java.util.List;

public class ExpenseList {

	private List<Expens> listOfExpenses = new LinkedList<>();
	
	public List<Expens> getListOfExpenses() {
		return listOfExpenses;
	}

	public void setListOfExpenses(List<Expens> listOfExpenses) {
		this.listOfExpenses = listOfExpenses;
	}
	
}
