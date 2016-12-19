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
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@Column
	private String name;
	
	@ElementCollection
	private List<Double> amount;
		
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Period period;
	
	@Column
	private String comment;

	@ManyToOne
	private ExpenseCategory expenseCategoryOwner;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the amount
	 */
//	public float getAmount() {
//		return amount;
//	}
//
//	/**
//	 * @param amount the amount to set
//	 */
//	public void setAmount(float amount) {
//		this.amount = amount;
//	}

	/**
	 * @return the period
	 */
	public Period getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(Period period) {
		this.period = period;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the expenseCategoryOwner
	 */
	public ExpenseCategory getExpenseCategoryOwner() {
		return expenseCategoryOwner;
	}

	/**
	 * @param expenseCategoryOwner the expenseCategoryOwner to set
	 */
	public void setExpenseCategoryOwner(ExpenseCategory expenseCategoryOwner) {
		this.expenseCategoryOwner = expenseCategoryOwner;
	}

	
	

	

}
