package model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class ExpenseItem extends Item {


	private ExpenseCategory category;

	public ExpenseCategory getCategory() {
		return category;
	}

	public void setCategory(ExpenseCategory category) {
		this.category = category;
	}

	

}
