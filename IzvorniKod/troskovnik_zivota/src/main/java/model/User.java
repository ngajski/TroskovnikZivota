package model;

import java.util.LinkedList;
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
	private String password;

	/**
	 * Firts name.
	 */
	@Column(nullable = false)
	private String firstName;

	/**
	 * Last name.
	 */
	@Column(nullable = false)
	private String lastName;

	/**
	 * OIB
	 */
	@Column(nullable = true)
	private String oib;

	/**
	 * Date of birth in format dd/mm/yyyy.
	 */
	@Column(nullable = true)
	private String dateOfBirth;

	/**
	 * Address.
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Address address;

	/**
	 * Telefon.
	 */
	@Column(nullable = true)
	private String telefon;

	/**
	 * Email.
	 */
	@Column(nullable = false)
	private String email;

	/**
	 * List of {@linkplain ExpenseList}s that belong to this User.
	 */
	@OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ExpenseList> expenseLists;

	public User() {
		super();
	}

	public User(String username, String password, String firstName, String lastName, String oib, String dateOfBirth,
			Address address, String telefon, String email) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.oib = oib;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.telefon = telefon;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOib() {
		return oib;
	}

	public void setOib(String oib) {
		this.oib = oib;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ExpenseList> getExpenseLists() {
		return expenseLists;
	}

	public void setExpenseLists(List<ExpenseList> expenseLists) {
		this.expenseLists = expenseLists;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

	public void addExpenseList(ExpenseList expenseList) {
		if (expenseLists == null) {
			expenseLists = new LinkedList<>();
		}
		expenseLists.add(expenseList);
	}

}
