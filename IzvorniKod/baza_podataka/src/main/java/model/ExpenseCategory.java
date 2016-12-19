package model;

import java.util.List;

import javax.persistence.Column;
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
	
	@Column
	private String name;
	
	@Column
	private boolean fixed;
		
	@ManyToOne
	private ExpenseCategory superCategory;
	
	@OneToMany(mappedBy="superCategory")
	private List<ExpenseCategory> subCategories;

	@OneToMany(mappedBy="expenseCategoryOwner")
	private List<ExpenseItem> expenseItems;
	
	@ManyToOne
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
	

	
	



}
