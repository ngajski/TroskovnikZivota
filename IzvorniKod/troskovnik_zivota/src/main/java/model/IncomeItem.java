package model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

	@Column
	private String name;
	
	@Column
	private String startDate;
	
	@Column
	private String endDate;
	
	@ElementCollection
	private List<Double> amounts;
	
	@Enumerated(EnumType.STRING)
	private Period period;
	
	@Column
	private boolean fixed;
	
	@Column
	private String comment;
	
	@ManyToOne
	private ExpenseList expenseListOwner;

	public IncomeItem() {
		super();
	}
	
	public IncomeItem(Long id, String name, String startDate, String endDate, List<Double> amounts, Period period,
			boolean fixed, String comment, ExpenseList expenseListOwner) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amounts = amounts;
		this.period = period;
		this.fixed = fixed;
		this.comment = comment;
		this.expenseListOwner = expenseListOwner;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public ExpenseList getExpenseListOwner() {
		return expenseListOwner;
	}

	public void setExpenseListOwner(ExpenseList expenseListOwner) {
		this.expenseListOwner = expenseListOwner;
	}

	@Override
	public String toString() {
		return "IncomeItem [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amounts=" + amounts + ", period=" + period + ", fixed=" + fixed + ", comment=" + comment
				+ ", expenseListOwner=" + expenseListOwner + "]";
	}



	
}
