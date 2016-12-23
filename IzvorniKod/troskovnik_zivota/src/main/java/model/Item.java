package model;

import java.util.List;


import javax.persistence.Column;
import javax.persistence.ElementCollection;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import model.time.Period;


@MappedSuperclass
public abstract class Item {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;
	
	@ElementCollection
	protected List<Double> amounts;
	
	@Enumerated(EnumType.STRING)
	protected Period period;
	
	@Column
	protected boolean fixed;
	
	@Column
	private String comment;
	
	public Item() {
		super();
	}
	
	public Item(Long id, String name, List<Double> amounts, Period period,
			boolean fixed, String comment) {
		super();
		this.id = id;
		this.name = name;
		this.amounts = amounts;
		this.period = period;
		this.fixed = fixed;
		this.comment = comment;
		
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

	public List<Double> getAmounts() {
		return amounts;
	}

	public void setAmounts(List<Double> amounts) {
		this.amounts = amounts;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public boolean getFixed() {
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
	
}
