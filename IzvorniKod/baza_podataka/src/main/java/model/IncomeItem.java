package model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class IncomeItem extends Item {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
