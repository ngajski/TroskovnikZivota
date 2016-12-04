package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "expenses_lists")
public class ExpenseList {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	/**
	 * KADA VIDIS ATTR mappedBy to znaci da se razredu koji je el liste(razred
	 * A) nalazi FK na ovaj razred i zato se dodatno u razredu A (u ovom sadu
	 * slucaju u nadrazredu Item) pise clanska varijabla tipa ovaj razred na
	 * kojem pise ManyToOne (tak se realizira list s jpa)
	 */
	@OneToMany(mappedBy = "expenseListOwner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ExpenseCategory> expenseCategories;

	/**
	 * KADA VIDIS ATTR mappedBy to znaci da se razredu koji je el liste(razred
	 * A) nalazi FK na ovaj razred i zato se dodatno u razredu A (u ovom sadu
	 * slucaju u nadrazredu Item) pise clanska varijabla tipa ovaj razred na
	 * kojem pise ManyToOne (tak se realizira list s jpa)
	 */
	@OneToMany(mappedBy = "expenseListOwner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<IncomeItem> incomes;

	
	@ManyToOne
	private User userOwner;

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

	public List<IncomeItem> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<IncomeItem> incomes) {
		this.incomes = incomes;
	}

	/**
	 * @return the userOwner
	 */
	public User getUserOwner() {
		return userOwner;
	}

	/**
	 * @param userOwner the userOwner to set
	 */
	public void setUserOwner(User userOwner) {
		this.userOwner = userOwner;
	}



	
}
