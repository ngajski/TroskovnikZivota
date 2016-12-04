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
	private String password;

	/**
	 * password
	 */
	@Column(nullable = true)
	private String firstName;

	/**
	 * password
	 */
	@Column(nullable = true)
	private String lastName;

	@Column(nullable = true)
	private String OIB;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date dateOfBirth;

	/**
	 * ovdje ne pise mappedBy pa je ovaj razred sadrzi FK na address, ni u
	 * razredu address nema nikakve notacije <br>
	 * ---- pogledaj dolje list -----
	 */
	@ManyToOne
	private Address address;

	@Column(nullable = true)
	private String telefon;

	/**
	 * password
	 */
	@Column(nullable = true)
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

	public String getOIB() {
		return OIB;
	}

	public void setOIB(String oIB) {
		OIB = oIB;
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

}
