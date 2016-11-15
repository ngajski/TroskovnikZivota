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
	@Column(nullable = false)
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

	@Column
	private String OIB;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;

	/**
	 * employe ima kljuc na address, u db strani kljuc user::id nece imati nad
	 * sobom UNIQUE - legalno da vise usera na istoj address zivi
	 */
	@ManyToOne
	private Address adress;

	private String telefon;

	/**
	 * password
	 */
	@Column(nullable = false)
	private String email;

	/**
	 * list of blog entries this user has created and published
	 */
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
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

	public Address getAdress() {
		return adress;
	}

	public void setAdress(Address adress) {
		this.adress = adress;
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
