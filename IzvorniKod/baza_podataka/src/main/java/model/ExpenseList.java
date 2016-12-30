package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import model.time.Date;

@Entity
@Table(name = "expense_lists")
public class ExpenseList {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;
	
	@OneToMany(mappedBy = "expenseListOwner", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ExpenseCategory> expenseCategories;

	@OneToMany(mappedBy = "expenseListOwner", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<IncomeItem> incomeItems;

	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private User userOwner;
	
	@Column
	private Boolean writable;

	public Boolean getWritable() {
		return writable;
	}

	public void setWritable(Boolean writable) {
		this.writable = writable;
	}

	public ExpenseList() {
		super();
	}

	public ExpenseList(Long id, String name, List<ExpenseCategory> expenseCategories, List<IncomeItem> incomeItems,
			User userOwner) {
		super();
		this.id = id;
		this.name = name;
		this.expenseCategories = expenseCategories;
		this.incomeItems = incomeItems;
		this.userOwner = userOwner;
	}
	
	public ExpenseList(Long id, String name, User userOwner) {
		super();
		this.id = id;
		this.name = name;
		this.expenseCategories = new ArrayList<>();
		this.incomeItems = new ArrayList<>();
		this.userOwner = userOwner;
	}
	
	public ExpenseList(String name, User userOwner) {
		super();
		this.name = name;
		this.expenseCategories = new ArrayList<>();
		this.incomeItems = new ArrayList<>();
		this.userOwner = userOwner;
	}
	
	/**
	 * Adds new category to this {@link ExpenseList}, and sets this to be
	 * the owner of given {@link ExpenseCategory}.
	 * 
	 * @param category {@link ExpenseCategory}
	 */
	public void addNewCategory(ExpenseCategory category) {
		if (category != null) {
			category.setExpenseListOwner(this);
		}
		
		//TODO
		if (expenseCategories == null) {
			expenseCategories = new ArrayList<>();
		}
		
		expenseCategories.add(category);
	}
	
	/**
	 * Adds new category to this {@link ExpenseList}, and sets this to be
	 * the owner of given {@link ExpenseCategory}.
	 * 
	 * @param category {@link ExpenseCategory}
	 */
	public void addNewIncomeItem(IncomeItem item) {
		if (item != null) {
			item.setExpenseListOwner(this);
		}
		
		if (incomeItems == null) {
			incomeItems = new ArrayList<>();
		}
		
		incomeItems.add(item);
	}
	
	/**
	 * Returns map of all months and their summed expenses. 
	 * 
	 * KEY = month (int)
	 * VALUE = amount (double)
	 * 
	 * <p>
	 * USE: 
	 * If you want to sum  fixed expenses, function parameter <code>fixed</code> must be true.
	 * If you want to sum  variable expenses, function parameter <code>fixed</code> must be false.
	 * If you want to sum  all expenses, function parameter <code>fixed</code> must be null.
	 *
	 * @param startDate
	 * @param stopDate
	 * @param expenseByMonth MUST be new {@link HashMap}.
	 * @param categories
	 * @param fixed true, false, or null
	 * @return
	 */
	public Map<String,Double> findExpenseAmount(String startDate, String stopDate,Map<String,Double> expenseByMonth,
			List<ExpenseCategory> categories, Boolean fixed) {
		
		if (categories == null || categories.isEmpty()) {
			return expenseByMonth;
		}
		
		for (ExpenseCategory expenseCategory : categories) {
			findExpenseAmount(startDate, stopDate, expenseByMonth, expenseCategory.getSubCategories(),fixed);
		}

		for (ExpenseCategory expenseCategory : categories) {
			List<ExpenseItem> items = expenseCategory.getExpenseItems();
			for (ExpenseItem item : items) {
				
				if (fixed != null) {
					if (item.isFixed() != fixed) {
						continue;
					}
				}
				
				int payingMonths = Date.difference2(startDate, stopDate) + 1;
				
				String date = new String(startDate);
				for (Integer monthNumber = 0; monthNumber < payingMonths; monthNumber = monthNumber+1) {
					Double oldExpense = expenseByMonth.get(date);
					if (oldExpense == null) {
						oldExpense = new Double(0.0);
					}
					
					Double expense = item.getExpenseForDate(date);
					expenseByMonth.put(new String(date), oldExpense + expense); 
					date = Date.nextMonth(date);
					
				}
			}
		}
		
		return expenseByMonth;
	}
	
	/**
	 * Returns map of all months and their summed incomes. 
	 * 
	 * KEY = month (int)
	 * VALUE = amount (double)
	 * 
	 * USE: 
	 * If you want to sum  salary incomes, function parameter <code>salary</code> must be true.
	 * If you want to sum  other incomes, function parameter <code>salary</code> must be false.
	 * If you want to sum  all incomes, function parameter <code>salary</code> must be null.
	 *
	 * @param startDate
	 * @param stopDate
	 * @param salary true,false or null
	 * @return
	 */
	public Map<String, Double> findIncomeAmount(String startDate, String stopDate, Boolean sallary) {
		Map<String, Double> incomeByMonth = new LinkedHashMap<>();

		for (IncomeItem item : incomeItems) {

			if (sallary != null) {
				if (item.isSallary() != sallary) {
					continue;
				}
			}

			int payingMonths = Date.difference2(startDate, stopDate) + 1;

			String dateForIteration = new String(startDate);
			for (Integer monthNumber = 0; monthNumber < payingMonths; monthNumber = monthNumber + 1) {
				Double oldExpense = incomeByMonth.get(dateForIteration);
				if (oldExpense == null) {
					oldExpense = new Double(0.0);
				}

				Double expense = item.getIncomeForDate(dateForIteration);
				incomeByMonth.put(new String(dateForIteration), oldExpense + expense);
				dateForIteration = Date.nextMonth(dateForIteration);

			}
		}
		return incomeByMonth;
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

	public List<ExpenseCategory> getExpenseCategories() {
		return expenseCategories;
	}

	public void setExpenseCategories(List<ExpenseCategory> expenseCategories) {
		this.expenseCategories = expenseCategories;
	}

	public List<IncomeItem> getIncomeItems() {
		return incomeItems;
	}

	public void setIncomeItems(List<IncomeItem> incomeItems) {
		this.incomeItems = incomeItems;
	}

	public User getUserOwner() {
		return userOwner;
	}
	
	public void setUserOwner(User user) {
		this.userOwner = user;
	}
	

	@Override
	public String toString() {
		return "ExpenseList [id=" + id + ", name=" + name + ", expenseCategories=" + expenseCategories
				+ ", incomeItems=" + incomeItems + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((incomeItems == null) ? 0 : incomeItems.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userOwner == null) ? 0 : userOwner.hashCode());
		result = prime * result + ((writable == null) ? 0 : writable.hashCode());
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
		ExpenseList other = (ExpenseList) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (incomeItems == null) {
			if (other.incomeItems != null)
				return false;
		} else if (!incomeItems.equals(other.incomeItems))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userOwner == null) {
			if (other.userOwner != null)
				return false;
		} else if (!userOwner.equals(other.userOwner))
			return false;
		if (writable == null) {
			if (other.writable != null)
				return false;
		} else if (!writable.equals(other.writable))
			return false;
		return true;
	}

	/**
	 * Sets this id and id's of <code>incomeItems<code> 
	 * and id's of all <code>expenseItems<code> to null.
	 * 
	 *  Sets owners of all <code>incomeItems<code> 
	 *  and <code>expenseItems<code>.
	 * 
	 */
	public void revalidate(User user) {
		this.userOwner = user;
		this.id = null;
		
		for (IncomeItem item : incomeItems) {
			item.revalidate(this);
		}
		
		for (ExpenseCategory category : expenseCategories) {
			System.out.println(category.getSuperCategory());
			if (category.getSuperCategory() == null) {
				category.revalidate(this, null);
			}
		}
	}
	
}
