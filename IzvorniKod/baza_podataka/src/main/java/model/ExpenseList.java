package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ExpenseItem> expenses;

	/**
	 * KADA VIDIS ATTR mappedBy to znaci da se razredu koji je el liste(razred
	 * A) nalazi FK na ovaj razred i zato se dodatno u razredu A (u ovom sadu
	 * slucaju u nadrazredu Item) pise clanska varijabla tipa ovaj razred na
	 * kojem pise ManyToOne (tak se realizira list s jpa)
	 */
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<IncomeItem> incomes;

	/**
	 * reference to blog user which created this blog entry
	 */
	@ManyToOne
	private User owner;

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

	public List<ExpenseItem> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<ExpenseItem> expenses) {
		this.expenses = expenses;
	}

	public List<IncomeItem> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<IncomeItem> incomes) {
		this.incomes = incomes;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	
}
