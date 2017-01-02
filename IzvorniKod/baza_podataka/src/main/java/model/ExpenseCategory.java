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
	
	@OneToMany(mappedBy="superCategory", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<ExpenseCategory> subCategories;

	@OneToMany(mappedBy="expenseCategoryOwner", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<ExpenseItem> expenseItems;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private ExpenseList expenseListOwner;
	
	@Column
	private Long ownerID;
	
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
	
	public ExpenseCategory(String name, boolean fixed, ExpenseList expenseListOwner) {
		super();
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
		
	}
	
	/**
	 * Adds new {@link ExpenseItem} to the list of {@link #expenseItems} and
	 * sets fixed of this to given item.
	 * 
	 * @param item
	 */
	public void addExpenseItem(ExpenseItem item) {
		item.setExpenseCategoryOwner(this);
		if (this.fixed) {
			item.setFixed(this.fixed);
		}
		
		if (expenseItems == null) {
			expenseItems = new ArrayList<>();
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

	public boolean getFixed() {
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
	
	
	public Long getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(Long ownerID) {
		this.ownerID = ownerID;
	}

	@Override
	public String toString() {
		return "ExpenseCategory: name=" + name; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expenseListOwner == null) ? 0 : expenseListOwner.hashCode());
		result = prime * result + (fixed ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((superCategory == null) ? 0 : superCategory.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpenseCategory other = (ExpenseCategory) obj;
		if (expenseListOwner == null) {
			if (other.expenseListOwner != null)
				return false;
		} else if (!expenseListOwner.equals(other.expenseListOwner))
			return false;
		if (fixed != other.fixed)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (superCategory == null) {
			if (other.superCategory != null)
				return false;
		} else if (!superCategory.equals(other.superCategory))
			return false;
		return true;
	}

	/**
	 * Set this <code>id<code> to null and </code>expenseListOwner</code> to given owner.
	 * If this has <code>expenseCategoryOwner</code> it will also be set.
	 * 
	 * Calls <code>revalidate()</code> of all {@link ExpenseItem} and {@link ExpenseCategory}
	 * recursively.
	 * 
	 */

	public void revalidate(ExpenseList expenseListOwner, ExpenseCategory categoryOwner) {

		System.out.println("Kategorija:" + this.name);
		for (ExpenseItem item : expenseItems) {
			item.revalidate(this);
		}

		if (!subCategories.isEmpty()) {
			for (ExpenseCategory category : subCategories) {
				if (category.getOwnerID().equals(this.id)) {
					category.revalidate(expenseListOwner, this);
				}
			}
		}

		this.id = null;
		this.expenseListOwner = expenseListOwner;
		this.superCategory = categoryOwner;
		
		if (categoryOwner == null) {
			this.ownerID = new Long(expenseListOwner.getId());
		} else {
			this.ownerID = new Long(categoryOwner.getId());
		}
		
		System.out.println(this.name);
		System.out.println("Troskovnik vlasnik: " + this.expenseListOwner.getName());
		if (categoryOwner != null) {
			System.out.println("Kategorija vlasnik: " +  categoryOwner.getName());
		}
	}
}
