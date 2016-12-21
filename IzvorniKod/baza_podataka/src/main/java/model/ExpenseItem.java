package model;

import java.util.ArrayList;
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

import model.time.Date;


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

	/** The name. */
	@Column
	private String name;
	
	/** The start date. */
	@Column
	private Date startDate;
	
	/** The end date. */
	@Column
	private Date endDate;
	
	/** The amounts. */
	@ElementCollection
	private List<Double> amounts;
		
	/** The period. */
	@Enumerated(EnumType.STRING)
	private Period period;

	/** The comment. */
	@Column
	private String comment;

	/** The expense category owner. */
	@ManyToOne
	private ExpenseCategory expenseCategoryOwner;

	/** The fixed. */
	@Column
	private boolean fixed;

	/**
	 * Instantiates a new expense item.
	 */
	public ExpenseItem() {
		super();
	}

	public ExpenseItem(Long id, String name, Date startDate, Date endDate, Period period,
			String comment, ExpenseCategory expenseCategoryOwner,boolean fixed) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amounts = new ArrayList<>();
		this.period = period;
		this.comment = comment;
		this.expenseCategoryOwner = expenseCategoryOwner;
		this.fixed = checkOwnerCategory(fixed);
		
		if (period == Period.ONE_TIME) {
			endDate = startDate;
		}
	}
	
	public ExpenseItem(Long id, String name, Date startDate, Date endDate, Period period,
			String comment,boolean fixed) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amounts = new ArrayList<>();
		this.period = period;
		this.comment = comment;
		this.expenseCategoryOwner = null;
		this.fixed = checkOwnerCategory(fixed);
		
		if (period == Period.ONE_TIME) {
			endDate = startDate;
		}
	}
	
	/**
	 * Returns expense amount for selected month.
	 * 
	 * @param date selected {@link Date}
	 * @return expense amount {@link Double}
	 * 
	 */
	public Double getExpenseForDate(Date date) {
		
		if (period == Period.ONE_TIME && date.equals(startDate)) {
			return amounts.get(0);
		} else if (period != Period.ONE_TIME && 
				date.happenedAfter(startDate) && date.happenedBefore(endDate)) {

			if (fixed) {
				return amounts.get(0);
			}
			
			int payingMonth = date.difference(startDate);
			
			return amounts.get(payingMonth);
		} else {
			return new Double(0.0);
		}
	}

	/**
	 * Checks if owner category is fixed. If it is then this item is also fixed, else
	 * this item {@link #fixed} is determined with parameter given through 
	 * constructor.
	 * 
	 * @param fixed
	 * @return is it fixed
	 */
	private boolean checkOwnerCategory(boolean fixed) {
		if (expenseCategoryOwner != null && expenseCategoryOwner.isFixed()) {
			return true;
		} else {
			return fixed;
		}
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
	
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	public boolean isFixed() {
		return fixed;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	@Override
	public String toString() {
		return this.name + ": " + this.comment;
	}
	
}
