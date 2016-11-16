package model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class ExpenseCategory {

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private boolean fixed;
	
	//nepotrebno !!
	//@OneToMany(mappedBy="superCategory")
	//private List<Item> expenses;
	
	@ManyToOne
	private ExpenseCategory superCategory;
	
	@OneToMany(mappedBy="superCategory")
	private List<ExpenseCategory> subCategories;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
