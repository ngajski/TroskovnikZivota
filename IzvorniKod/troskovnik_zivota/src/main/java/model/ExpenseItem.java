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

import org.hibernate.annotations.CollectionId;

@Entity
@Table(name = "expenses_items")
public class ExpenseItem {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	@Column
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
	private String comment;

	@ManyToOne
	private ExpenseCategory expenseCategoryOwner;

	
	public ExpenseItem() {
		super();
	}

	public ExpenseItem(Long id, String name, String startDate, String endDate, List<Double> amounts, Period period,
			String comment, ExpenseCategory expenseCategoryOwner) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amounts = amounts;
		this.period = period;
		this.comment = comment;
		this.expenseCategoryOwner = expenseCategoryOwner;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ExpenseCategory getExpenseCategoryOwner() {
		return expenseCategoryOwner;
	}

	public void setExpenseCategoryOwner(ExpenseCategory expenseCategoryOwner) {
		this.expenseCategoryOwner = expenseCategoryOwner;
	}

	
}
