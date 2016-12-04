package model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User {
	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * username
	 */
	@Column(nullable = false, unique = true)
	private String username;

	/**
	 * password
	 */
	@Column(nullable = false)
	private String firstName;

	/**
	 * password
	 */
	@Column(nullable = false)
	private String lastName;
	
	/**
	 * password
	 */
	@Column(nullable = false)
	private String email;

	/**
	 * KADA VIDIS ATTR mappedBy to znaci da se razredu koji je el liste(razred
	 * A) nalazi FK na ovaj razred i zato se dodatno razredu A pise clanska
	 * varijabla tipa ovaj razred na kojem pise ManyToOne (tak se realizira list
	 * s jpa)
	 */
	@OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ExpenseList> expenseLists;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the expenseLists
	 */
	public List<ExpenseList> getExpenseLists() {
		return expenseLists;
	}

	/**
	 * @param expenseLists the expenseLists to set
	 */
	public void setExpenseLists(List<ExpenseList> expenseLists) {
		this.expenseLists = expenseLists;
	}

	
}
