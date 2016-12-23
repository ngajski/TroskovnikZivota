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

	/**
	 * id
	 */
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

	
	/**
	 * Adds new category to this {@link ExpenseList}, and sets this to be
	 * the owner of given {@link ExpenseCategory}.
	 * 
	 * @param category {@link ExpenseCategory}
	 */
	public void addNewCategory(ExpenseCategory category) {
		if (category != null) {
			category.setExpenseListOwner(this);
			expenseCategories.add(category);
		}
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
			incomeItems.add(item);
		}
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
	public Map<Date,Double> findExpenseAmount(Date startDate, Date stopDate,Map<Date,Double> expenseByMonth,
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
				
				int payingMonths = startDate.difference(stopDate) + 1;
				
				Date date = new Date(startDate);
				for (Integer monthNumber = 0; monthNumber < payingMonths; monthNumber = monthNumber+1) {
					Double oldExpense = expenseByMonth.get(date);
					if (oldExpense == null) {
						oldExpense = new Double(0.0);
					}
					
					Double expense = item.getExpenseForDate(date);
					expenseByMonth.put(new Date(date), oldExpense + expense); 
					date.nextMonth();
					
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
	public Map<Date, Double> findIncomeAmount(Date startDate, Date stopDate, Boolean sallary) {
		Map<Date, Double> incomeByMonth = new LinkedHashMap<>();

		for (IncomeItem item : incomeItems) {

			if (sallary != null) {
				if (item.isSallary() != sallary) {
					continue;
				}
			}

			int payingMonths = startDate.difference(stopDate) + 1;

			Date date = new Date(startDate);
			for (Integer monthNumber = 0; monthNumber < payingMonths; monthNumber = monthNumber + 1) {
				Double oldExpense = incomeByMonth.get(date);
				if (oldExpense == null) {
					oldExpense = new Double(0.0);
				}

				Double expense = item.getIncomeForDate(date);
				incomeByMonth.put(new Date(date), oldExpense + expense);
				date.nextMonth();

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
	
}
