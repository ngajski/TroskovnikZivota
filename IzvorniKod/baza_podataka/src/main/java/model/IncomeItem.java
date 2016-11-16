package model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class IncomeItem {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
    @Enumerated(EnumType.STRING)
	private Period period;
	
	private float amount;
	
	private boolean fixed;
	
	private String comment;
	
	private String type;

	public String getType() {
		return type;
	}

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

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
