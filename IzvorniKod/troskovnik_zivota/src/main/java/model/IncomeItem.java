package model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import model.time.Date;
import model.time.Period;

@Entity
@Table(name = "income_items")
public class IncomeItem {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;
	
	@ElementCollection
	@CollectionTable(name ="income_amounts")
	protected List<Double> amounts;
	
	@Enumerated(EnumType.STRING)
	protected Period period;
	
	@Column
	protected boolean fixed;
	
	@Column
	private String comment;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private ExpenseList expenseListOwner;

	@Column
	private String startDate;
	
	@Column
	private String endDate;
	
	@Column
	private boolean sallary;

	public IncomeItem() {
		super();
	}
	
	public IncomeItem(Long id, String name, List<Double> amounts, Period period,
			boolean fixed, String comment) {
		super();
		this.id = id;
		this.name = name;
		this.amounts = amounts;
		this.period = period;
		this.fixed = fixed;
		this.comment = comment;
	}	
	
	public IncomeItem(Long id, String name, String startDate, String endDate, List<Double> amounts, Period period,
			boolean sallary, String comment,ExpenseList expenseListOwner) {
	this(id, name, amounts, period, false, comment);
		
		this.endDate = endDate;
		this.startDate = startDate;
		this.expenseListOwner = expenseListOwner;
		this.sallary = sallary;
		
		if (period == Period.ONE_TIME) {
			endDate = startDate;
		}
	}
	
	public IncomeItem(String name, String startDate, String endDate, List<Double> amounts, Period period,
			boolean sallary, String comment,ExpenseList expenseListOwner) {
	this(null, name, amounts, period, false, comment);
		
		this.endDate = endDate;
		this.startDate = startDate;
		this.expenseListOwner = expenseListOwner;
		this.sallary = sallary;
		
		if (period == Period.ONE_TIME) {
			endDate = startDate;
		}
	}
	
	/**
	 * Returns income amount for given date: mm/yyyy
	 * 
	 * @param date selected {@link Date}
	 * @return expense amount {@link Double}
	 * 
	 */
	public Double getIncomeForDate(String date) {

		if (period == Period.ONE_TIME && date.equals(startDate)) {
			return amounts.get(0);
		} else if (period != Period.ONE_TIME && Date.happenedAfter(date, startDate)
				&& Date.happenedBefore(date, endDate)) {
			if (fixed) {
				return amounts.get(0);
			}
			
			int payingMonth = Date.difference2(this.startDate,date);

			return amounts.get(payingMonth);
		} else {
			return new Double(0.0);
		}
	}
	
	/**
	 * Returns summed up income amounts between <code>startDate</code>
	 * and <code>endDate</code>
	 * @param startDate
	 * @param stopDate
	 * @return
	 */
	public Double getIncomeBetweenDates(String startDate,String stopDate) {
		double income = 0;
		
		while (Date.happenedBefore(startDate, stopDate)) {
			income += this.getIncomeForDate(startDate);
			startDate = Date.nextMonth(startDate);
		}
		
		return income;
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

	public ExpenseList getExpenseListOwner() {
		return expenseListOwner;
	}

	public void setExpenseListOwner(ExpenseList expenseListOwner) {
		this.expenseListOwner = expenseListOwner;
	}

	public boolean isSallary() {
		return sallary;
	}

	public void issetSallary(boolean sallary) {
		this.sallary = sallary;
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

	public boolean isFixed() {
		return fixed;
	}

	public void issetFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setSallary(boolean sallary) {
		this.sallary = sallary;
	}

	@Override
	public String toString() {
		return this.getName() + " " + this.getComment();
	}

	/**
	 * Sets this id to null and <code>expenseListOwner<code>
	 * to given owner.
	 * 
	 * @param owner expenseList owner of this
	 */
	public void revalidate(ExpenseList owner) {
		this.id = null;
		this.expenseListOwner = owner;
	}

	public void validateAmounts() {
		if (this.fixed) {
			return;
		} else if (this.period == Period.MONTHLY) {
			return;
		}
		
		List<Double> validatedAmounts = new ArrayList<>();
		int payingMonths = Date.difference2(startDate, endDate) + 1;
		
		int position = 0;
		if (period == Period.QUARTARLY) {
			for (int i = 0; i < payingMonths; ++i) {
				if (i % 3 == 0 && position < amounts.size()) {
					validatedAmounts.add(amounts.get(position));
					position++;
				} else {
					validatedAmounts.add(new Double(0));
				}
			}
		} else if (period == Period.ANUALY) {
			for (int i = 0; i < payingMonths; ++i) {
				if (i % 12 == 0 && position < amounts.size()) {
					validatedAmounts.add(amounts.get(position));
					position++;
				} else {
					validatedAmounts.add(new Double(0));
				}
			}
		} else {
			return;
		}
		
		this.amounts = validatedAmounts; 
		
	}

	
}
