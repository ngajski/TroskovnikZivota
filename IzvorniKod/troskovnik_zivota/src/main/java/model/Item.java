package model;

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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import model.time.Date;
import model.time.Period;


@Entity
public abstract class Item {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Date startDate;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Date endDate;
	
	@ElementCollection
	private List<Double> amounts;
	
	@Enumerated(EnumType.STRING)
	private Period period;
	
	@Column
	private boolean fixed;
	
	@Column
	private String comment;
	
	public Item() {
		super();
	}
	
	public Item(Long id, String name, Date startDate, Date endDate, List<Double> amounts, Period period,
			boolean fixed, String comment) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amounts = amounts;
		this.period = period;
		this.fixed = fixed;
		this.comment = comment;
		
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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
	
}
