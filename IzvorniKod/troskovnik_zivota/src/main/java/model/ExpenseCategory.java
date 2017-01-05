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

	@OneToMany(mappedBy = "superCategory", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ExpenseCategory> subCategories;

	@OneToMany(mappedBy = "expenseCategoryOwner", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
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

		// if (fixed) {
		// category.issetFixed(fixed);
		// }

		category.setSuperCategory(this);
		category.setExpenseListOwner(this.getExpenseListOwner());
		// category.setOwnerID(new Long(this.id));

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
			item.issetFixed(this.fixed);
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

	public boolean isgetFixed() {
		return fixed;
	}

	public void issetFixed(boolean fixed) {
		// if (fixed) {
		// for (ExpenseCategory subCategory : subCategories) {
		// subCategory.issetFixed(fixed);
		// }
		// }

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

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public String toString() {
		return "ExpenseCategory [id=" + id + ", name=" + name + ", fixed=" + fixed + ", subCategories=" + subCategories
				+ ", expenseItems=" + expenseItems + "]";
	}

	// /**
	// * Set this <code>id<code> to null and </code>expenseListOwner</code> to
	// given owner.
	// * If this has <code>expenseCategoryOwner</code> it will also be set.
	// *
	// * Calls <code>revalidate()</code> of all {@link ExpenseItem} and {@link
	// ExpenseCategory}
	// * recursively.
	// *
	// */
	//
	// public void revalidate(ExpenseList expenseListOwner, ExpenseCategory
	// categoryOwner) {
	//
	// System.out.println("Kategorija:" + this.name);
	// for (ExpenseItem item : expenseItems) {
	// item.revalidate(this);
	// }
	//
	// if (!subCategories.isEmpty()) {
	// for (ExpenseCategory category : subCategories) {
	// if (category.getOwnerID().equals(this.id)) {
	// category.revalidate(expenseListOwner, this);
	// }
	// }
	// }
	//
	// this.id = null;
	// this.expenseListOwner = expenseListOwner;
	// this.superCategory = categoryOwner;
	//
	// if (categoryOwner == null) {
	// this.ownerID = new Long(expenseListOwner.getId());
	// } else {
	// this.ownerID = new Long(categoryOwner.getId());
	// }
	//
	// System.out.println(this.name);
	// System.out.println("Troskovnik vlasnik: " +
	// this.expenseListOwner.getName());
	// if (categoryOwner != null) {
	// System.out.println("Kategorija vlasnik: " + categoryOwner.getName());
	// }
	// }
}
