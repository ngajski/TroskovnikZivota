package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "expenses_categories")
public class ExpenseCategory {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private boolean fixed;
	
		
	@ManyToOne
	private ExpenseCategory superCategory;
	
	@OneToMany(mappedBy="superCategory")
	private List<ExpenseCategory> subCategories;

	@OneToMany(mappedBy="expenseCategoryOwner")
	private List<ExpenseItem> expenseItems;
	
	@ManyToOne
	private ExpenseList expenseListOwner;
	
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
	 * @return the expenseItems
	 */
	public List<ExpenseItem> getExpenseItems() {
		return expenseItems;
	}

	/**
	 * @param expenseItems the expenseItems to set
	 */
	public void setExpenseItems(List<ExpenseItem> expenseItems) {
		this.expenseItems = expenseItems;
	}

	/**
	 * @return the superCategory
	 */
	public ExpenseCategory getSuperCategory() {
		return superCategory;
	}

	/**
	 * @param superCategory the superCategory to set
	 */
	public void setSuperCategory(ExpenseCategory superCategory) {
		this.superCategory = superCategory;
	}

	/**
	 * @return the subCategories
	 */
	public List<ExpenseCategory> getSubCategories() {
		return subCategories;
	}

	/**
	 * @param subCategories the subCategories to set
	 */
	public void setSubCategories(List<ExpenseCategory> subCategories) {
		this.subCategories = subCategories;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}


	public ExpenseList getExpenseListOwner() {
		return expenseListOwner;
	}

	public void setExpenseListOwner(ExpenseList expenseListOwner) {
		this.expenseListOwner = expenseListOwner;
	}
	
	



}
