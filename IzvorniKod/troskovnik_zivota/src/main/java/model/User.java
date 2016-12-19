package model;

import java.util.Date;
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
	private String password;

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

	@Column(nullable = true)
	private String oib;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dateOfBirth;

	/**
	 * ovdje ne pise mappedBy pa je ovaj razred sadrzi FK na address, ni u
	 * razredu address nema nikakve notacije <br>
	 * ---- pogledaj dolje list -----
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Address address;

	@Column(nullable = true)
	private String telefon;

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
	@OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	private List<ExpenseList> expenseLists;

	public User() {
		super();
	}

	public User(String username, String password, String firstName, String lastName, String oib, Date dateOfBirth,
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

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getAddress() {
		return address;
	}

	public void setAdress(Address address) {
		this.address = address;
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
				+ lastName + ", oib=" + oib + ", dateOfBirth=" + dateOfBirth + ", address=" + address + ", telefon="
				+ telefon + ", email=" + email + ", expenseLists=" + expenseLists + "]";
	}
	
	public void addExpenseList(ExpenseList expenseList){
		if (expenseLists == null){
			expenseLists = new LinkedList<>();
		}
		expenseLists.add(expenseList);
	}

}
