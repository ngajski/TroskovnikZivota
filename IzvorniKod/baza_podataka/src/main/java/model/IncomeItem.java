package model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "income_items")
public class IncomeItem {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@ElementCollection
	private List<Double> amount;

	private boolean fixed;
	
    @Enumerated(EnumType.STRING)
	private Period period;
		
	private String comment;
	
	@ManyToOne
	private ExpenseList expenseListOwner;


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


	
}
