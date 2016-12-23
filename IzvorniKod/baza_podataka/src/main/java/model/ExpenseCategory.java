package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "expense_categories")
public class ExpenseCategory {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private boolean fixed;
		
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private ExpenseCategory superCategory;
	
	@OneToMany(mappedBy="superCategory", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<ExpenseCategory> subCategories;

	@OneToMany(mappedBy="expenseCategoryOwner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<ExpenseItem> expenseItems;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private ExpenseList expenseListOwner;
	
	public ExpenseCategory() {
		super();
	}

	public ExpenseCategory(Long id, String name, boolean fixed, ExpenseCategory superCategory,
			List<ExpenseCategory> subCategories, List<ExpenseItem> expenseItems, ExpenseList expenseListOwner) {
		super();
		this.id = id;
		this.name = name;
		this.fixed = fixed;
		this.superCategory = superCategory;
		this.subCategories = subCategories;
		this.expenseItems = expenseItems;
		this.expenseListOwner = expenseListOwner;
	}
	
	public ExpenseCategory(Long id, String name, boolean fixed, ExpenseList expenseListOwner) {
		super();
		this.id = id;
		this.name = name;
		this.fixed = fixed;
		this.superCategory = null;
		this.subCategories = new ArrayList<>();
		this.expenseItems = new ArrayList<>();
		this.expenseListOwner = expenseListOwner;
	}

	
	/**
	 * Adds new sub category to this {@link ExpenseCategory}, and sets
	 * {@link ExpenseList} owner of this to be the owner of given
	 * {@link ExpenseCategory}.
	 * 
	 * @param category
	 *            {@link ExpenseCategory}
	 */
	public void addExpenseSubcategory(ExpenseCategory category) {
		if (category == null) {
			return;
		}
		
		if (fixed) {
			category.setFixed(fixed);
		}
		
		category.setSuperCategory(this);
		category.setExpenseListOwner(this.getExpenseListOwner());
		subCategories.add(category);
	}
	
	/**
	 * Adds new {@link ExpenseItem} to the list of {@link #expenseItems} and
	 * sets fixed of this to given item.
	 * 
	 * @param item
	 */
	public void addExpenseItem(ExpenseItem item) {
		if (expenseItems == null) {
			expenseItems = new ArrayList<>();
		}
		
		item.setExpenseCategoryOwner(this);
		if (this.fixed) {
			item.setFixed(this.fixed);
		}
		expenseItems.add(item);
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

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		if (fixed) {
			for (ExpenseCategory subCategory : subCategories) {
				subCategory.setFixed(fixed);
			}
		}
		
		this.fixed = fixed;
	}

	public ExpenseCategory getSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(ExpenseCategory superCategory) {
		this.superCategory = superCategory;
	}
	
	public List<ExpenseCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<ExpenseCategory> subCategories) {
		this.subCategories = subCategories;
	}


	public List<ExpenseItem> getExpenseItems() {
		return expenseItems;
	}

	public void setExpenseItems(List<ExpenseItem> expenseItems) {
		this.expenseItems = expenseItems;
	}

	public ExpenseList getExpenseListOwner() {
		return expenseListOwner;
	}

	public void setExpenseListOwner(ExpenseList expenseListOwner) {
		this.expenseListOwner = expenseListOwner;
	}
	
	
	@Override
	public String toString() {
		return "ExpenseCategory [id=" + id + ", name=" + name + ", fixed=" + fixed + ", superCategory=" + superCategory
				+ ", subCategories=" + subCategories + ", expenseItems=" + expenseItems + ", expenseListOwner="
				+ expenseListOwner + "]";
	}
}
